package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkedTree implements Tree {

    public static final int DEPTH = 15;

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
                children.add(createBranch((new LinkedTreeNode()).setParent(node), depth + 1));
            }
            node.setChildren(children);
        }
        node.generate(childCount != 0);
        return node;
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
        clone.current = clone.root;
        return clone;
    }

    @Override
    public Tree mutate() {
        mutate(root);
        return this;
    }

    public void mutate(LinkedTreeNode node){
        if (Math.random() <= Tree.MUTATION_RATE) {
            node.generate(node.getChildren().size() != 0);
        }
        node.getChildren().forEach(this::mutate);
    }

    @Override
    public Tree crossOver(Tree other) {
        double random = Math.random();
        Tree clone = clone();
        Tree otherClone = other.clone();
        LinkedTree target = (LinkedTree) (random < 0.5 ? clone: otherClone);
        LinkedTree origin = (LinkedTree) (random < 0.5 ? otherClone: clone);

        LinkedTreeNode targetNode = (LinkedTreeNode) target.toList().get((int) (Math.random() * target.getNodeCount()));
        LinkedTreeNode originNode = (LinkedTreeNode) origin.toList().get((int) (Math.random() * origin.getNodeCount()));

        //Todo: Change tree size after crossover
        targetNode.clone(originNode);

        return target;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int evaluate() {
        return evaluate(root);
    }

    @Override
    public List<TreeNode> toList() {
        return toList(root);
    }

    public List<TreeNode> toList(LinkedTreeNode node){
        ArrayList<TreeNode> nodes = new ArrayList<>(List.of(node));
        for (LinkedTreeNode child: node.getChildren()){
            nodes.addAll(toList(child));
        }
        return nodes;
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
