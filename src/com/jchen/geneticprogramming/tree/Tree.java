package com.jchen.geneticprogramming.tree;

public interface Tree {

    TreeNode getCurrent();

    TreeNode getChild(int child);

    TreeNode getParent();

    TreeNode getRoot();

    Tree clone();

    Tree mutate();

    Tree crossOver(Tree other);

    Tree setCurrent(TreeNode node);

    int getNodeCount();
}
