package com.jchen.geneticprogramming.tree;

import java.util.List;

public class LinkedTreeNode extends TreeNode {

    private LinkedTreeNode parent;
    private List<LinkedTreeNode> children;

    public LinkedTreeNode(double data, boolean isFunction) {
        super(data, isFunction);
    }

    public LinkedTreeNode(double data) {
        super(data);
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
        LinkedTreeNode clone = new LinkedTreeNode(getData(), isFunction());
        clone.setChildren(children.stream().map(LinkedTreeNode::clone).toList());
        return clone;
    }
}
