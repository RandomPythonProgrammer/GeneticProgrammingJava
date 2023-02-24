package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.List;

public class LinkedTree implements Tree {

    public static final int DEPTH = 7;

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
            if (random >= ((double) depth) / (DEPTH * 10)) {
                childCount = 2;
            }
            for (int i = 0; i < childCount; i++) {
                nodeCount++;
                children.add(createBranch((new LinkedTreeNode()).setParent(node), depth + 1));
            }
            node.setChildren(children);
        }
        node.generate(childCount != 0);
        return node;
    }

    @Override
    public Tree clone() {
        LinkedTree clone = new LinkedTree(false);
        clone.root = root.clone();
        clone.nodeCount = nodeCount;
        clone.current = clone.root;
        return clone;
    }

    @Override
    public Tree mutate(double rate) {
        mutate(root, rate);
        return this;
    }

    private void mutate(LinkedTreeNode node, double rate) {
        if (Math.random() <= rate) {
            node.generate(node.getChildren().size() != 0);
        }
        node.getChildren().forEach((child) -> mutate(child, rate));
    }

    @Override
    public Tree crossOver(Tree other) {
        double random = Math.random();
        Tree clone = clone();
        Tree otherClone = other.clone();
        LinkedTree target = (LinkedTree) (random < 0.5 ? clone : otherClone);
        LinkedTree origin = (LinkedTree) (random < 0.5 ? otherClone : clone);

        List<TreeNode> targetNodes = target.toList();
        List<TreeNode> originNodes = origin.toList();

        double pos = Math.random();

        LinkedTreeNode targetNode = (LinkedTreeNode) targetNodes.get((int) (pos * targetNodes.size()));
        LinkedTreeNode originNode = (LinkedTreeNode) originNodes.get((int) (pos * originNodes.size()));

        //Todo: Change tree size after crossover
        targetNode.clone(originNode);

        return target;
    }

    @Override
    public int getNodeCount() {
        return getNodeCount(root);
    }

    private int getNodeCount(LinkedTreeNode node) {
        int nodes = 1;
        for (LinkedTreeNode child: node.getChildren()){
            nodes += getNodeCount(child);
        }
        return nodes;
    }

    @Override
    public int evaluate(int input) {
        return evaluate(root, input);
    }

    @Override
    public List<TreeNode> toList() {
        return toList(root);
    }

    private List<TreeNode> toList(LinkedTreeNode node) {
        ArrayList<TreeNode> nodes = new ArrayList<>(List.of(node));
        for (LinkedTreeNode child : node.getChildren()) {
            nodes.addAll(toList(child));
        }
        return nodes;
    }

    private int evaluate(LinkedTreeNode node, int input) {
        if (node.isFunction()) {
            int a = evaluate(node.getChild(0), input);
            int b = evaluate(node.getChild(1), input);
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
            if (node.getData().equals("input")) {
                return input;
            } else {
                return Integer.parseInt(node.getData());
            }
        }
        System.out.println("Failed to evaluate");
        return 0;
    }
}
