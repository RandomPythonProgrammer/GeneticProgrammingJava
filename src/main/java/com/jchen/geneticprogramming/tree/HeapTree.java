package com.jchen.geneticprogramming.tree;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeapTree implements Tree {

    public static final int DEPTH = 5;
    protected TreeNode[] nodes;
    public static final int THREADS = 12;
    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREADS);;
    public CyclicBarrier barrier;
    protected int size;

    public HeapTree(boolean generate) {
        barrier = new CyclicBarrier(THREADS + 1);
        size = 0;
        for (int i = 0; i < DEPTH; i++) {
            size += Math.pow(2, i);
        }
        nodes = new TreeNode[size];
        if (generate) {
            for (int i = 0; i < size; i++) {
                nodes[i] = (new TreeNode()).generate(i < size - Math.pow(2, DEPTH-1));
            }
        }
    }

    @Override
    public Tree clone() {
        barrier.reset();
        HeapTree clone = new HeapTree(false);
        try {
            int work = Math.ceilDiv(size, THREADS);
            for (int i = 0; i < THREADS; i++) {
                int index = i;
                HeapTree.EXECUTOR.submit(() -> {
                    for (int j = work * index; j < size && j < work * (index+1); j++) {
                        clone.nodes[j] = nodes[j].clone();
                    }
                    try {
                        barrier.await();
                    } catch (BrokenBarrierException | InterruptedException e) {
                        System.getLogger("HeapTree").log(System.Logger.Level.ERROR, "Failed to clone tree");
                    }
                });
            }
            barrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            System.getLogger("HeapTree").log(System.Logger.Level.ERROR, "Failed to clone tree");
        }
        return clone;
    }

    @Override
    public Tree mutate(double rate) {
        for (int i = 0; i < size; i++) {
            if (Math.random() <= rate) {
                nodes[i] = (new TreeNode()).generate(i < size - Math.pow(2, DEPTH - 1));
            }
        };
        return this;
    }

    @Override
    public Tree crossOver(Tree other) {
        int index = (int) (Math.random() * nodes.length);

        double random = Math.random();
        Tree clone = clone();
        Tree otherClone = other.clone();
        HeapTree target = (HeapTree) (random < 0.5 ? clone: otherClone);
        HeapTree origin = (HeapTree) (random < 0.5 ? otherClone: clone);

        crossOver(index, origin, target);
        return target;
    }

    protected void crossOver(int index, HeapTree origin, HeapTree target) {
        if (index < origin.getNodeCount()) {
            target.nodes[index] = origin.nodes[index];
            crossOver(2 * index + 1, origin, target);
            crossOver(2 * index + 2, origin, target);
        }
    }


    @Override
    public int getNodeCount() {
        return nodes.length;
    }

    @Override
    public int evaluate(int input) {
        return evaluate(0, input);
    }

    @Override
    public List<TreeNode> toList() {
        return null;
    }

    private int evaluate(int nodeIndex, int input) {
        TreeNode node = nodes[nodeIndex];
        if (node.isFunction()) {
            int a = evaluate(nodeIndex * 2 + 1, input);
            int b = evaluate(nodeIndex * 2 + 2, input);
            switch (node.getData()) {
                case "or":
                    return a | b;
                case "xor":
                    return a ^ b;
                case "nor":
                    return ~(a | b);
                case "xnor":
                    return ~(a ^ b);
                case "and":
                    return a & b;
                case "nand":
                    return ~(a & b);
            }
        } else {
            if (node.getData().equals("input")){
                return input;
            } else {
                return Integer.parseInt(node.getData());
            }
        }
        System.out.println("Failed to evaluate");
        return 0;
    }
}
