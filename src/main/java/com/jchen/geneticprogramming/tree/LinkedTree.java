package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkedTree implements Tree {

    public static final int DEPTH = 20;

    private LinkedTreeNode root;
    private LinkedTreeNode current;
    private int nodeCount;

    public LinkedTree(boolean generate) {
        nodeCount = 0;
        if (generate) {
            nodeCount++;
            root = createBranch(new LinkedTreeNode(), 1);
        }
        current = root;
    }

    public LinkedTreeNode createBranch(LinkedTreeNode node, int depth) {
        int childCount = 0;
        if (depth < this.DEPTH) {
            ArrayList<LinkedTreeNode> children = new ArrayList<>();
            double random = Math.random();
            if (random >= ((double) depth)/(DEPTH * 10)) {
                childCount = 2;
            }
            for (int i = 0; i < childCount; i++) {
                nodeCount++;
                children.add(createBranch(new LinkedTreeNode(), depth + 1));
            }
            node.setChildren(children);
        }
        node.generate(childCount != 0);
        return node;
    }

    @Override
    public LinkedTreeNode getCurrent() {
        return current;
    }

    @Override
    public LinkedTreeNode getChild(int child) {
        return current = current.getChild(child);
    }

    @Override
    public LinkedTreeNode getParent() {
        return current = current.getParent();
    }

    @Override
    public LinkedTreeNode getRoot() {
        return current = root;
    }

    public LinkedTreeNode getNode(List<Integer> indexes) {
        current = root;
        for (int i : indexes) {
            current = current.getChild(i);
        }
        return root;
    }

    @Override
    public Tree clone() {
        LinkedTree clone = new LinkedTree(false);
        clone.root = root.clone();
        clone.nodeCount = nodeCount;
        return clone;
    }

    @Override
    public Tree mutate() {
        return null;
    }

    @Override
    public Tree crossOver(Tree other) {
        return null;
    }

    @Override
    public Tree setCurrent(TreeNode node) {
        current = (LinkedTreeNode) node;
        return this;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int evaluate() {
        return evaluate(root);
    }

    public int evaluate(LinkedTreeNode node) {
        if (node.isFunction()) {
            int a = evaluate(node.getChild(0));
            int b = evaluate(node.getChild(1));
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
