package com.jchen.geneticprogramming.tree;

public class TreeNode {

    public static final String[] functions = {"or", "nor", "xor", "xnor", "and", "nand"};
    private String data;

    public TreeNode(String data) {
        this.data = data;
    }

    public TreeNode(){
        this("");
    }

    public TreeNode generate(boolean isFunction){
        if (isFunction) {
            data = functions[(int) (Math.random() * functions.length)];
        } else {
            if (Math.random() > 0.25) {
                data = String.valueOf((int) Math.round(Math.random() * Integer.MAX_VALUE));
            } else {
                String[] keys = Tree.VARIABLES.keySet().toArray(new String[0]);
                data = keys[(int) (Math.random() * keys.length)];
            }
        }
        return this;
    }

    public String getData() {
        return data;
    }

    public TreeNode setData(String data) {
        this.data = data;
        return this;
    }

    public TreeNode clone() {
        return new TreeNode(data);
    }

    public boolean isFunction(){
        for (String function: functions){
            if (data.equals(function)){
                return true;
            }
        }
        return false;
    }

    public TreeNode clone(TreeNode other) {
        data = other.data;
        return this;
    }

    public static int or(TreeNode a, TreeNode b) {
        return Integer.parseInt(a.getData()) | Integer.parseInt(b.getData());
    }

    public static int nor(TreeNode a, TreeNode b){
        return ~or(a, b);
    }

    public static int xnor(TreeNode a, TreeNode b) {
        return ~(xor(a, b));
    }

    public static int xor(TreeNode a, TreeNode b){
        return Integer.parseInt(a.getData()) ^ Integer.parseInt(b.getData());
    }

    public static int and(TreeNode a, TreeNode b){
        return Integer.parseInt(a.getData()) & Integer.parseInt(b.getData());
    }

    public static int nand(TreeNode a, TreeNode b){
        return ~and(a, b);
    }
}
