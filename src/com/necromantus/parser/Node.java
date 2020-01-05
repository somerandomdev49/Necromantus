package com.necromantus.parser;

public class Node {
    public final NodeType nodeType;
    public final Object value;
    public final Object nodeType2;
    public Node left, right;
    public Node parent;

    private Node(Node parent, NodeType nodeType, Object nodeType2, Object value) {
        this.parent = parent;
        this.nodeType = nodeType;
        this.nodeType2 = nodeType2;
        this.value = value;
    }

    public Node(NodeType nodeType, Object nodeType2, Object value) {
        this(null, nodeType, nodeType2, value);
    }

    public Node(NodeType nodeType, Object nodeType2, Object value, Node left, Node right) {
        this(nodeType, nodeType2, value);
        if (left != null) {
            this.left = left;
            this.left.parent = this;
        }
        if (right != null) {
            this.right = right;
            this.right.parent = this;
        }
    }

    public void add(boolean isRight, Node node) {
        node.parent = this;
        if (isRight) right = node;
        else left = node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeType=" + nodeType +
                ", value=" + value +
                ", nodeType2=" + nodeType2 +
                '}';
    }
}
