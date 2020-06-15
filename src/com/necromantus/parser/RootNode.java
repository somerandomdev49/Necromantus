package com.necromantus.parser;

import java.util.ArrayList;

public class RootNode extends Node {
    public ArrayList<Node> children = new ArrayList<>();
    public RootNode(NodeType nodeType, Object nodeType2, Object value) {
        super(nodeType, nodeType2, value, 0);

    }

    public void add(Node node) {
        children.add(node);
        node.parent = this;
    }
}
