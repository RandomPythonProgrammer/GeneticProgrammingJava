package com.jchen.geneticprogramming.test;

import com.jchen.csv.Csv;
import com.jchen.geneticprogramming.tree.HeapTree;
import com.jchen.geneticprogramming.tree.LinkedTree;

import java.util.List;


public class EvaluationTest {
    public static void main(String[] args) {
        Csv csv = new Csv();
        csv.addLine(List.of("Linked Tree Scaled Time:", "Heap Tree Scaled Time:", "Linked Tree / Heap Tree Ratio:"));

        for (int i = 0; i < 100; i++) {
            //Testing the LinkedTree
            LinkedTree linkedTree = new LinkedTree(true);
            long linkedTreeStart = System.currentTimeMillis();
            int linkedTreeResult = linkedTree.evaluate();
            long linkedTreeTime = System.currentTimeMillis() - linkedTreeStart;
            double linkedTreeScaledTime = ((double) linkedTreeTime) / linkedTree.getNodeCount();

            //Testing the HeapTree
            HeapTree heapTree = new HeapTree(true);
            long heapTreeStart = System.currentTimeMillis();
            int heapTreeResult = heapTree.evaluate();
            long heapTreeTime = System.currentTimeMillis() - heapTreeStart;
            double heapTreeScaledTime = ((double) heapTreeTime) / heapTree.getNodeCount();

            csv.addLine(List.of(String.valueOf(linkedTreeScaledTime), String.valueOf(heapTreeScaledTime), String.valueOf(linkedTreeScaledTime / heapTreeScaledTime)));
        }

        csv.write("C:\\Users\\Joshua\\Desktop\\Evaluation_Data.csv");
        System.out.println("Finished Data Collection");
    }
}
