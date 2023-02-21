package com.jchen.geneticprogramming.tree;

import java.util.ArrayList;
import java.util.List;

public class LinkedTreeNode extends TreeNode {

    private LinkedTreeNode parent;
    private List<LinkedTreeNode> children;

    public LinkedTreeNode(LinkedTreeNode parent, String data) {
        super(data);
        this.parent = parent;
        children = new ArrayList<>();
    }

    public LinkedTreeNode(String data){
        this(null, data);
    }

    public LinkedTreeNode() {
        this(null, "");
    }

    public LinkedTreeNode getParent() {
        return parent;
    }

    public LinkedTreeNode setParent(LinkedTreeNode parent) {
        this.parent = parent;
        return this;
    }

    public List<LinkedTreeNode> getChildren() {
        return children;
    }

    public LinkedTreeNode setChildren(List<LinkedTreeNode> children) {
        this.children = children;
        return this;
    }

    public LinkedTreeNode getChild(int child) {
        return this.children.get(child);
    }

    @Override
    public LinkedTreeNode clone() {
        LinkedTreeNode clone = new LinkedTreeNode(getData());
        clone.setChildren(children.stream().map((node) -> node.clone().setParent(clone)).toList());
        return clone;
    }

    @Override
    public LinkedTreeNode clone(TreeNode other){
        super.clone(other);
        LinkedTreeNode node = (LinkedTreeNode) other;
        this.setChildren(node.getChildren());
        this.setParent(node.getParent());
        return this;
    }
}
