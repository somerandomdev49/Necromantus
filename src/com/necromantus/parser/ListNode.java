package com.necromantus.parser;

import java.util.ArrayList;

public class ListNode extends Node {
    public ArrayList<Node> children = new ArrayList<>();
    public ListNode(NodeType nodeType, Object nodeType2, Object value, int line) {
        super(nodeType, nodeType2, value, line);

    }

    public void add(Node node) {
        children.add(node);
        node.parent = this;
    }
}
