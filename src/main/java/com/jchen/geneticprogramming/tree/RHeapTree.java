package com.jchen.geneticprogramming.tree;

public class RHeapTree extends HeapTree{
    public RHeapTree(boolean generate) {
        super(generate);
    }

    @Override
    public Tree crossOver(Tree other) {
        int index = 0;
        int level = (int) Math.round(Math.random() * DEPTH);
        for (int i = 0; i < level-1; i++)
            index += Math.pow(2, i);

        int index1 = index + (int) (Math.random() * Math.pow(2, level-1));
        int index2 = index + (int) (Math.random() * Math.pow(2, level-1));

        double random = Math.random();
        Tree clone = clone();
        Tree otherClone = other.clone();
        HeapTree target = (HeapTree) (random < 0.5 ? clone: otherClone);
        HeapTree origin = (HeapTree) (random < 0.5 ? otherClone: clone);

        crossOver(index1, index2, origin, target);
        return target;
    }

    protected void crossOver(int index1, int index2, HeapTree origin, HeapTree target) {
        if (index1 < origin.getNodeCount() && index2 < origin.getNodeCount()) {
            target.nodes[index1] = origin.nodes[index2];
            crossOver(2 * index1 + 1, 2 * index2 + 1, origin, target);
            crossOver(2 * index1 + 2, 2 * index2 + 1, origin, target);
        }
    }
}
