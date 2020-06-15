package com.necromantus.directors;

import com.necromantus.lang.NMNativeFunc;
import com.necromantus.lang.Scope;
import com.necromantus.runtime.Runtime;
import com.necromantus.runtime.Walker;
import com.necromantus.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StdLibDirector extends Director {
    public Tokenizer t;

    public StdLibDirector(Tokenizer t) {
        this.t = t;
    }

    @Override
    public void fill(Walker walker) {
        walker.putNativeFunc(new NMNativeFunc("write",  (ArrayList<Object> args) -> {for(Object arg:args){System.out.print(arg);}System.out.print("\n");return null;}));
        walker.putNativeFunc(new NMNativeFunc("writes",  (ArrayList<Object> args) -> {for(Object arg:args){System.out.print(arg);}return null;}));

        walker.putNativeFunc(new NMNativeFunc("read",   (ArrayList<Object> args) -> new Scanner(System.in).nextLine()));

        walker.putNativeFunc(new NMNativeFunc("run",  (ArrayList<Object> args) -> {
            try {
                new Runtime(false, false, (String)args.get(0), t.tokenInfos).run();
            } catch(Exception e) {
                System.err.println("Error!");
            }
            return null;
        }));
        walker.putNativeFunc(new NMNativeFunc("stof",  (ArrayList<Object> args) -> {
            try {
                return Float.parseFloat((String)args.get(0));
            } catch(Exception e) {
                System.err.println("Unable to convert from string to float!");
            }
            return null;
        }));
        walker.putNativeFunc(new NMNativeFunc("toString",  (ArrayList<Object> args) -> args.get(0).toString()));

        walker.putNativeFunc(new NMNativeFunc("newMap", (args) -> new HashMap<String, Object>()));
        walker.putNativeFunc(new NMNativeFunc("mapPut", (args) -> (
                (HashMap<String, Object>) args.get(0)).put((String)args.get(1), args.get(2))
        ));
        walker.putNativeFunc(new NMNativeFunc("mapGet", (args) -> (
                (HashMap<String, Object>) args.get(0)).get(args.get(1))
        ));
        walker.putNativeFunc(new NMNativeFunc("mapDel", (args) -> (
                (HashMap<String, Object>) args.get(0)).remove(args.get(1))
        ));


        walker.putNativeFunc(new NMNativeFunc("newList", (args) -> new ArrayList<Object>()));
        walker.putNativeFunc(new NMNativeFunc("listAdd", (args) -> (
                (List<Object>) args.get(0)).add(args.get(1))
        ));
        walker.putNativeFunc(new NMNativeFunc("listGet", (args) -> (
                (List<Object>) args.get(0)).get((int)(float)args.get(1))
        ));
        walker.putNativeFunc(new NMNativeFunc("listSet", (args) -> (
                (List<Object>) args.get(0)).set((int)(float)args.get(1), args.get(2))
        ));
        walker.putNativeFunc(new NMNativeFunc("listDel", (args) -> (
                (List<Object>) args.get(0)).remove((int)(float)args.get(1))
        ));
        walker.putNativeFunc(new NMNativeFunc("listSize", (args) -> (
                (List<Object>) args.get(0)).size()
        ));
    }
}
