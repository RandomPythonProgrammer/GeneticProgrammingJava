package com.jchen.geneticprogramming.test;

import com.jchen.csv.Csv;
import com.jchen.geneticprogramming.tree.HeapTree;
import com.jchen.geneticprogramming.tree.LinkedTree;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.List;

public class CloneTest {
    public static void main(String[] args) {

        Csv csv = new Csv();
        csv.addLine(List.of("Linked Tree Scaled Time:", "Heap Tree Scaled Time:", "Linked Tree / Heap Tree Ratio:"));

        for (int i = 0; i < 5000; i++) {
            //Testing the LinkedTree
            LinkedTree linkedTree = new LinkedTree(true);
            long linkedTreeStart = System.currentTimeMillis();
            Tree linkedTreeClone = linkedTree.clone();
            long linkedTreeTime = System.currentTimeMillis() - linkedTreeStart;
            double linkedTreeScaledTime = ((double) linkedTreeTime) / linkedTreeClone.getNodeCount();

            //Testing the HeapTree
            HeapTree heapTree = new HeapTree(true);
            long heapTreeStart = System.currentTimeMillis();
            Tree heapTreeClone = heapTree.clone();
            long heapTreeTime = System.currentTimeMillis() - heapTreeStart;
            double heapTreeScaledTime = ((double) heapTreeTime) / heapTreeClone.getNodeCount();

            csv.addLine(List.of(String.valueOf(linkedTreeScaledTime), String.valueOf(heapTreeScaledTime), String.valueOf(linkedTreeScaledTime / heapTreeScaledTime)));
        }

        csv.write("C:\\Users\\Joshua\\Desktop\\Data.csv");
        System.out.println("Finished Data Collection");
    }
}
