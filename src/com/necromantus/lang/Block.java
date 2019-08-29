package com.necromantus.lang;

import com.necromantus.parser.BlockNode;
import com.necromantus.parser.Node;
import com.necromantus.runtime.Walker;

public class Block {
    public BlockNode node;
    public Scope scope;
    public Walker walker;
    public Block(Scope parentScope, BlockNode node) {
        this.node = node;
        this.scope = new Scope(parentScope);
        this.walker = new Walker(scope);
    }

    public void walk() throws Exception {
        for (Node statement : node.children)
            walker.walk(statement);
    }
}
