package com.jchen.geneticprogramming.tree;

import java.util.List;

public interface Tree {

    int DEPTH = 10;
    int CHILDREN = 3;
    TreeNode getCurrent();
    TreeNode getChild(int child);
    TreeNode getParent();
    TreeNode getRoot();
    TreeNode getNode(List<Integer> indexes);
    Tree clone();
    Tree mutate();
    Tree crossOver(Tree other);

    Tree setCurrent(TreeNode node);
}
