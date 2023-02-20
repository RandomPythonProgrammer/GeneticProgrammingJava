package com.jchen.geneticprogramming.tree;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeapTree implements Tree {

    public static final int DEPTH = 20;

    private int current = 0;
    private TreeNode[] nodes;
    public static final int THREADS = 12;
    private ExecutorService executor;
    public CyclicBarrier barrier;
    private int size;

    public HeapTree(boolean generate) {
        executor = Executors.newFixedThreadPool(THREADS);
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
    public TreeNode getCurrent() {
        return nodes[0];
    }

    @Override
    public TreeNode getChild(int child) {
        return nodes[(current = 2 * current + 1 + child)];
    }

    @Override
    public TreeNode getParent() {
        return nodes[(current = (current - 1) / 2)];
    }

    @Override
    public TreeNode getRoot() {
        return nodes[current = 0];
    }

    @Override
    public Tree clone() {
        barrier.reset();
        HeapTree clone = new HeapTree(false);
        try {
            int work = Math.ceilDiv(size, THREADS);
            for (int i = 0; i < THREADS; i++) {
                executor.submit(() -> {
                    for (int j = work * THREADS; j < size && j < (work + 1) * THREADS; j++) {
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
    public Tree mutate() {
        for (int i = 0; i < size; i++) {
            if (Math.random() <= MUTATION_RATE) {
                nodes[i] = (new TreeNode()).generate(i < size - Math.pow(2, DEPTH - 1));
            }
        };
        return this;
    }

    @Override
    public Tree crossOver(Tree other) {
        return null;
    }

    @Override
    public Tree setCurrent(TreeNode node) {
        return null;
    }

    @Override
    public int getNodeCount() {
        return nodes.length;
    }

    @Override
    public int evaluate() {
        return evaluate(0);
    }

    public int evaluate(int nodeIndex) {
        TreeNode node = nodes[nodeIndex];
        if (node.isFunction()) {
            int a = evaluate(nodeIndex * 2 + 1);
            int b = evaluate(nodeIndex * 2 + 2);
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
            if (VARIABLES.containsKey(node.getData())){
                return VARIABLES.get(node.getData());
            } else {
                return Integer.parseInt(node.getData());
            }
        }
        System.out.println("Failed to evaluate");
        return 0;
    }
}
