package com.necromantus.lang;

import com.necromantus.parser.BlockNode;
import com.necromantus.parser.Node;
import com.necromantus.runtime.Walker;

import java.util.ArrayList;

public class NMFunc extends NMFunction {
    private final Walker walker;
    private Scope scope;
    private BlockNode node;
    public ArrayList<String> args;
    private int expectedArgSize;

    public NMFunc(BlockNode n, Scope parentScope, ArrayList<String> args) {
        scope = new Scope(parentScope);
        //                                                     ↓ makes sure that it is actually nonsense, not null. later i'll probably make nonsense better than just null.
        scope.vars.add(new NMVariable("out", scope.getVar("nonsense")));
        expectedArgSize = args.size();
        node = n;
        this.args = args;
        this.walker = new Walker(scope);
    }

    public Object call(ArrayList<Object> args, int argSize) throws Exception {
        if (this.args.size() != argSize)
            throw new Exception("function " + name + " requires " + this.args.size() + " arguments, but got only " + argSize + ".");
        int i = 0;
        for (Object arg : args) {
            this.walker.scope.vars.add(new NMVariable(this.args.get(i), arg));
            i++;
        }
        for (Node statement : node.children.subList(0, node.children.size()-2<1?1:node.children.size()-2))
            walker.walk(statement);
        return scope.getVar("out");
    }
}
