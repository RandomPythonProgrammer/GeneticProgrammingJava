package com.jchen.geneticprogramming.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Tree {
    double MUTATION_RATE = 0.2;

    Tree clone();

    Tree mutate(double rate);

    Tree crossOver(Tree other);

    int getNodeCount();

    int evaluate(int input);

    List<TreeNode> toList();
}
