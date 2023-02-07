package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.List;

public class LinkedTree implements Tree {

    private LinkedTreeNode root;
    private LinkedTreeNode current;
    private int childCount, depth;

    public LinkedTree(int childCount, int depth) {
        this.childCount = childCount;
        this.depth = depth;
        root = createBranch(new LinkedTreeNode(0), 1);
    }

    public LinkedTreeNode createBranch(LinkedTreeNode node, int depth) {
        if (depth <= this.depth) {
            ArrayList<LinkedTreeNode> children = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                children.add(createBranch(new LinkedTreeNode(0), depth + 1));
            }
            node.setChildren(children);
        }
        return node;
    }

    @Override
    public int getChildCount() {
        return childCount;
    }

    @Override
    public int getDepth() {
        return depth;
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
        return root;
    }

    @Override
    public LinkedTreeNode getNode(List<Integer> indexes) {
        current = root;
        for (int i: indexes){
            current = current.getChild(i);
        }
        return root;
    }

    @Override
    public Tree clone() {
        LinkedTree clone = new LinkedTree(childCount, depth);

    }

    @Override
    public Tree mutate() {
        return null;
    }

    @Override
    public Tree crossOver(Tree other) {
        return null;
    }
}
