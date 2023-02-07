package com.jchen.geneticprogramming.tree;

public class TreeNode {
    private double data;
    private boolean isFunction;

    public TreeNode(double data, boolean isFunction){
        this.data = data;
        this.isFunction = isFunction;
    }

    public TreeNode(double data){
        this(data, false);
    }

    public double getData() {
        return data;
    }

    public TreeNode setData(double data) {
        this.data = data;
        return this;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public TreeNode setFunction(boolean function) {
        isFunction = function;
        return this;
    }
}
