package com.necromantus.parser;

public enum NodeType {
    ACTION,
    VALUE,
    ROOT,
    EXPRESSION, STATEMENT, ARG_LIST, FUNC, ARG_CALL_LIST, BLOCK, IF;

    public enum ValueNodeType {
        NUMBER,
        STRING, FUNC_CALL_EXPR, VAR
    }

    public enum ActionNodeType {
        FUNC_CALL,
        OPERATOR
    }

    public enum StatementNodeType {
        VAR_DEFINITION,
        VAR_ASSIGNMENT
    }
}
