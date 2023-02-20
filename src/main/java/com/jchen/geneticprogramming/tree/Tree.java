package com.jchen.geneticprogramming.tree;

import java.util.HashMap;
import java.util.Map;

public interface Tree {
    double MUTATION_RATE = 0.2;
    Map<String, Integer> VARIABLES = Map.of("a", 10);

    TreeNode getCurrent();

    TreeNode getChild(int child);

    TreeNode getParent();

    TreeNode getRoot();

    Tree clone();

    Tree mutate();

    Tree crossOver(Tree other);

    Tree setCurrent(TreeNode node);

    int getNodeCount();

    int evaluate();
}
