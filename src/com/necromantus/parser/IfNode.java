package com.necromantus.parser;

import java.util.ArrayList;

public class IfNode extends Node {
    public IfNode elseIf;
    public Node elseNode;

    public IfNode(Node left, Node right, IfNode elseIf, Node elseNode) {
        super(NodeType.IF, null, "IF", left, right);
        this.elseIf = elseIf;
        this.elseNode = elseNode;
    }
}
