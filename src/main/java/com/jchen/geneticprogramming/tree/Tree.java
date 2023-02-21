package com.jchen.geneticprogramming.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Tree {
    double MUTATION_RATE = 0.2;
    Map<String, Integer> VARIABLES = Map.of("a", 10);

    Tree clone();

    Tree mutate();

    Tree crossOver(Tree other);

    int getNodeCount();

    int evaluate();

    List<TreeNode> toList();
}
