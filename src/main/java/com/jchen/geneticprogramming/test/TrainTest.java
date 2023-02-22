package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.algorithm.GeneticOperations;
import com.jchen.geneticprogramming.tree.LinkedTree;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.Map;

public class TrainTest {
    public static void main(String[] args) {
        Map<Tree, Double> organisms = GeneticOperations.train(
                1000,
                LinkedTree.class,
                (x) -> 10 & x | 4,
                () -> (int) (Math.random() * 10),
                200, 4
        );

        for (int i = 0; i < 10; i++) {
            System.out.println(GeneticOperations.getWeighted(organisms));
        }
    }
}
