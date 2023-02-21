package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.tree.LinkedTree;

public class LinkedTreeTest {
    public static void main(String[] args) {
        LinkedTree tree = new LinkedTree(true);
        System.out.println(tree.getNodeCount());
        System.out.println(tree.toList().size());
        
        System.out.println("-------------------------------");
        
        LinkedTree copy = (LinkedTree) tree.clone();
        System.out.println(copy.getNodeCount());
        System.out.println(copy.toList().size());

        System.out.println("-------------------------------");

        copy.mutate();
        System.out.println(copy.getNodeCount());
        System.out.println(copy.toList().size());
    }
}
