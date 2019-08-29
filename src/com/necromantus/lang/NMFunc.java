package com.necromantus.lang;

import com.necromantus.parser.BlockNode;
import com.necromantus.parser.Node;
import com.necromantus.runtime.Walker;

import java.util.ArrayList;

public class NMFunc extends NMFunction {
    private Block block;
    public ArrayList<String> args;
    private int expectedArgSize;

    public NMFunc(BlockNode n, Scope parentScope, ArrayList<String> args) {
        block = new Block(parentScope, n);
        //                                                     ↓ makes sure that it is actually nonsense, not null. later i'll probably make nonsense better than just null.
        block.scope.vars.add(new NMVariable("out", block.scope.getVar("nonsense")));
        expectedArgSize = args.size();
        this.args = args;
    }

    public Object call(ArrayList<Object> args, int argSize) throws Exception {
        if (this.args.size() != argSize)
            throw new Exception("function " + name + " requires " + this.args.size() + " arguments, but got only " + argSize + ".");
        int i = 0;
        for (Object arg : args) {
            this.block.walker.scope.vars.add(new NMVariable(this.args.get(i), arg));
            i++;
        }
        block.walk();
        return block.scope.getVar("out").getValue();
    }
}
