package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.tree.LinkedTree;


public class EvaluationTest {
    public static void main(String[] args) {
        LinkedTree tree = new LinkedTree(true);
        System.out.println(tree.evaluate());
    }
}
