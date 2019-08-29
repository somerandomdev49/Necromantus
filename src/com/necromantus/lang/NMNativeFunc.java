package com.necromantus.lang;

import java.util.ArrayList;

/**
 * Represents function that is provided by Necromantus executor.
 */
public class NMNativeFunc extends NMFunction {

    private NativeFunction func;

    public NMNativeFunc(String name, NativeFunction func) {
        this.name = name;
        this.func = func;
    }

    public Object call(ArrayList<Object> args, int argSize) {
        Object forDebuggingToSeeResultOfFunctionExecution = func.call(args); // :D haha
        return forDebuggingToSeeResultOfFunctionExecution;
    }

    @FunctionalInterface
    public interface NativeFunction {
        Object call(ArrayList<Object> args);
    }
}
