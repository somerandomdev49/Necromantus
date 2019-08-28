package com.necromantus.lang;

import java.util.ArrayList;

public abstract class NMFunction extends Named {
    public abstract Object call(ArrayList<Object> args, int argSize) throws Exception;
    public Object call(ArrayList<Object> args) throws Exception {
        return call(args, args.size());
    }
}
