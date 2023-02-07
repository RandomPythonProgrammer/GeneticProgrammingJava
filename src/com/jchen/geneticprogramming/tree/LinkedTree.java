package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.List;

public class LinkedTree implements Tree {

    private LinkedTreeNode root;
    private LinkedTreeNode current;

    public LinkedTree(boolean generate) {
        if (generate)
            root = createBranch(new LinkedTreeNode(0), 1);
    }

    public LinkedTreeNode createBranch(LinkedTreeNode node, int depth) {
        if (depth < this.DEPTH) {
            ArrayList<LinkedTreeNode> children = new ArrayList<>();
            int childCount = (int) (Math.random() * CHILDREN);
            for (int i = 0; i < childCount; i++) {
                children.add(createBranch(new LinkedTreeNode(0), depth + 1));
            }
            node.setChildren(children);
        }
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

    @Override
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
}
