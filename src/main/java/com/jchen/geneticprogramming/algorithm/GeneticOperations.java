package com.jchen.geneticprogramming.algorithm;

import com.jchen.geneticprogramming.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class GeneticOperations {
    public static Map<Tree, Double> train(int epochs, Class<? extends Tree> tree, Function<Integer, Integer> function, IntSupplier input, int populationSize, int children) {
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

            System.out.println("----------------------------------");
            System.out.printf("Generation: %d\n", epoch);
            System.out.printf("Best: %f\n", Collections.max(organisms.values()));
            double sum = 0;
            for (double value: organisms.values()) {
                sum += value;
            }
            System.out.printf("Average: %f\n", sum/organisms.size());
            System.out.println("----------------------------------");

            if (epoch < epochs - 1) {
                HashMap<Tree, Double> nextGeneration = new HashMap<>();
                while (nextGeneration.size() < populationSize) {
                    Map.Entry<Tree, Double> parent1 = getWeighted(organisms);
                    Map.Entry<Tree, Double> parent2 = getWeighted(organisms);
                    double mutationRate = Math.pow(1 - (parent1.getValue() + parent2.getValue())/2, 5);
                    for (int i = 0; i < children; i++) {
                        nextGeneration.put(parent1.getKey().crossOver(parent2.getKey()).mutate(mutationRate), 0d);
                    }
                }
                organisms = nextGeneration;
            }
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
