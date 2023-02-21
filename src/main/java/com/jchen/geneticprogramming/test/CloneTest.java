package com.jchen.geneticprogramming.test;

import com.jchen.csv.Csv;
import com.jchen.geneticprogramming.tree.HeapTree;
import com.jchen.geneticprogramming.tree.LinkedTree;
import com.jchen.geneticprogramming.tree.Tree;

import java.util.List;

public class CloneTest {
    public static void main(String[] args) {

        System.Logger logger = System.getLogger("CloneTest");

        Csv csv = new Csv();
        csv.addLine(List.of(
                "Linked Tree Node Count",
                "Heap Tree Node Count",
                "Linked Tree Scaled Time (ns)",
                "Heap Tree Scaled Time (ns)",
                "Linked Tree / Heap Tree Ratio:"
        ));

        logger.log(System.Logger.Level.INFO, "Starting Data Collection");
        for (int i = 0; i < 10000; i++) {
            //Testing the LinkedTree
            LinkedTree linkedTree = new LinkedTree(true);
            long linkedTreeStart = System.nanoTime();
            Tree linkedTreeClone = linkedTree.clone();
            long linkedTreeTime = System.nanoTime() - linkedTreeStart;
            double linkedTreeScaledTime = ((double) linkedTreeTime) / linkedTreeClone.getNodeCount();

            //Testing the HeapTree
            HeapTree heapTree = new HeapTree(true);
            long heapTreeStart = System.nanoTime();
            Tree heapTreeClone = heapTree.clone();
            long heapTreeTime = System.nanoTime() - heapTreeStart;
            double heapTreeScaledTime = ((double) heapTreeTime) / heapTreeClone.getNodeCount();

            csv.addLine(List.of(
                    String.valueOf(linkedTreeClone.getNodeCount()),
                    String.valueOf(heapTreeClone.getNodeCount()),
                    String.valueOf(linkedTreeScaledTime),
                    String.valueOf(heapTreeScaledTime),
                    String.valueOf(linkedTreeScaledTime / heapTreeScaledTime)
            ));
            logger.log(System.Logger.Level.INFO, String.format("Collected %d data points", i+1));
        }

        csv.write("C:\\Users\\JC200\\Desktop\\Clone_Data2.csv");
        logger.log(System.Logger.Level.INFO, "Finished Data Collection");
    }
}
