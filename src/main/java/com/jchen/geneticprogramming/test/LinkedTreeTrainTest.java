package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.algorithm.GeneticOperations;
import com.jchen.geneticprogramming.tree.LinkedTree;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.Map;

public class LinkedTreeTrainTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Map<Tree, Double> organisms = GeneticOperations.train(
                    1000,
                    LinkedTree.class,
                    (x) -> 10 & x | 4,
                    () -> (int) (Math.random() * 10),
                    100,
                    2,
                    String.format("C:\\Users\\JC200\\Desktop\\Research Data\\train\\linkedtree\\run 2\\Linked_Tree_Train_%d.csv", i + 1)
            );
        }
    }
}
