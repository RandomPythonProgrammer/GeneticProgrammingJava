package com.jchen.geneticprogramming.test;

import com.jchen.geneticprogramming.tree.HeapTree;
import com.jchen.geneticprogramming.tree.LinkedTree;
import com.jchen.geneticprogramming.tree.Tree;

public class CloneTest {
    public static void main(String[] args) {

        //Testing the LinkedTree
        LinkedTree linkedTree = new LinkedTree(true);
        System.out.printf("Linked Tree Size: %d \n", linkedTree.getNodeCount());
        long linkedTreeStart = System.currentTimeMillis();
        Tree linkedTreeClone = linkedTree.clone();
        long linkedTreeTime = System.currentTimeMillis() - linkedTreeStart;
        System.out.printf("Cloning Linked Tree Took (Milliseconds): %d \n", linkedTreeTime);
        System.out.printf("Time Per Node Clone (Milliseconds/Node): %f\n", ((double) linkedTreeTime)/linkedTreeClone.getNodeCount());

        //Testing the HeapTree
        HeapTree heapTree = new HeapTree(true);
        System.out.printf("Heap Tree Size: %d \n", heapTree.getNodeCount());
        long heapTreeStart = System.currentTimeMillis();
        Tree heapTreeClone = heapTree.clone();
        long heapTreeTime = System.currentTimeMillis() - heapTreeStart;
        System.out.printf("Cloning Heap Tree Took (Milliseconds): %d \n", heapTreeTime);
        System.out.printf("Time Per Node Clone (Milliseconds/Node): %f\n", ((double) heapTreeTime)/heapTreeClone.getNodeCount());

    }
}
