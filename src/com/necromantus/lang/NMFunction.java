package com.necromantus.lang;

import java.util.ArrayList;

/**
 * Represent any function that can be called.
 */
public abstract class NMFunction extends Named {
    public abstract Object call(ArrayList<Object> args, int argSize) throws Exception;
    public Object call(ArrayList<Object> args) throws Exception {
        return call(args, args.size());
    }
}
