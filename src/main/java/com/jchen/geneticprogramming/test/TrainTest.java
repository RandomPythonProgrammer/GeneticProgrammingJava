package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.algorithm.GeneticOperations;
import com.jchen.geneticprogramming.tree.LinkedTree;

public class TrainTest {
    public static void main(String[] args) {
        GeneticOperations.train(100, LinkedTree.class, 10, 20, 2);
    }
}
