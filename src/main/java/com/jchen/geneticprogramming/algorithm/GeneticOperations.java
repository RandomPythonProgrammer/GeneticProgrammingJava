package com.jchen.geneticprogramming.algorithm;

import com.jchen.csv.Csv;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class GeneticOperations {

    public static Map<Tree, Double> train(int epochs, Class<? extends Tree> tree, Function<Integer, Integer> function, IntSupplier input, int populationSize, int children) {
        return train(epochs, tree, function, input, populationSize, children, null);
    }

    public static Map<Tree, Double> train(int epochs, Class<? extends Tree> tree, Function<Integer, Integer> function, IntSupplier input, int populationSize, int children, String log) {
        long start = System.currentTimeMillis();
        Csv csv = new Csv();
        csv.addLine(List.of(
                "Generation",
                "Population",
                "Best",
                "Average",
                "Elapsed Time (ms)"
        ));


        HashMap<Tree, Double> organisms = new HashMap<>();
        for (int i = 0; i < populationSize; i++) {
            try {
                organisms.put(tree.getDeclaredConstructor(Boolean.TYPE).newInstance(true), 0d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Map.Entry<Tree, Double> organism: organisms.entrySet()) {
                double sum = 0;
                for (int i = 0; i < 15; i++) {
                    int in = input.getAsInt();
                    sum += 1 - getError(organism.getKey().evaluate(in), function.apply(in));
                }

                organisms.put(organism.getKey(), sum/15);
            }

            double best = Collections.max(organisms.values());
            System.out.println("----------------------------------");
            System.out.printf("Generation: %d\n", epoch);
            System.out.printf("Population: %d\n", organisms.size());
            System.out.printf("Best: %f\n", best);
            double sum = 0;
            for (double value: organisms.values()) {
                sum += value;
            }
            double average = sum/organisms.size();
            System.out.printf("Average: %f\n", average);
            System.out.println("----------------------------------");

            csv.addLine(List.of(
                    String.valueOf(epoch),
                    String.valueOf(organisms.size()),
                    String.valueOf(best),
                    String.valueOf(average),
                    String.valueOf(System.currentTimeMillis() - start)
            ));

            if (best >= 0.95){
                break;
            }

            if (epoch < epochs - 1) {
                HashMap<Tree, Double> nextGeneration = new HashMap<>();
                while (nextGeneration.size() < populationSize) {
                    Map.Entry<Tree, Double> parent1 = getWeighted(organisms);
                    Map.Entry<Tree, Double> parent2 = getWeighted(organisms);
                    double mutationRate = Math.pow(1 - (parent1.getValue() + parent2.getValue())/2, 6);
                    for (int i = 0; i < children; i++) {
                        nextGeneration.put(parent1.getKey().crossOver(parent2.getKey()).mutate(mutationRate), 0d);
                    }
                }
                organisms = nextGeneration;
            }
        }

        if (log != null) {
            csv.write(log);
        }
        return organisms;
    }

    public static double getError(int value, int target) {
        double error = Math.pow(value - target, 2);
        return error / (error + 1);
    }

    public static Map.Entry<Tree, Double> getWeighted(Map<Tree, Double> data) {
        ArrayList<Map.Entry<Tree, Double>> items = new ArrayList<>(data.entrySet());
        Collections.shuffle(items);

        double sum = 0;
        for (double weight : data.values()) {
            sum += weight;
        }

        double random = Math.random() * sum;

        for (Map.Entry<Tree, Double> entry : items) {
            if ((random -= entry.getValue()) <= 0) {
                return entry;
            }
        }
        return null;
    }
}
