package com.jchen.geneticprogramming.algorithm;

import com.jchen.geneticprogramming.tree.Tree;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneticOperations {
    public static void train(int epochs, Class<? extends Tree> tree, int target, int populationSize, int children) {
        HashMap<Tree, Double> organisms = new HashMap<>();
        for (int i = 0; i < populationSize; i++) {
            try {
                organisms.put(tree.getDeclaredConstructor(Boolean.TYPE).newInstance(true), 0d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            organisms.replaceAll((k, v) -> 1 - getError(k.evaluate(), target));

            System.out.println(Collections.max(organisms.values()));

            HashMap<Tree, Double> nextGeneration = new HashMap<>();
            while (nextGeneration.size() < populationSize) {
                Tree parent1 = getWeighted(organisms);
                Tree parent2 = getWeighted(organisms);
                for (int i = 0; i < children; i++) {
                    nextGeneration.put(parent1.crossOver(parent2).mutate(), 0d);
                }
            }
            organisms = nextGeneration;
        }
    }

    public static double getError(int value, int target) {
        double error = Math.abs(value - target);
        return error / (error + 1);
    }

    public static Tree getWeighted(HashMap<Tree, Double> data) {
        double sum = 0;
        for (double weight : data.values()) {
            sum += weight;
        }

        double random = Math.random() * sum;

        for (Map.Entry<Tree, Double> entry : data.entrySet()) {
            if ((random -= entry.getValue()) <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }
}
