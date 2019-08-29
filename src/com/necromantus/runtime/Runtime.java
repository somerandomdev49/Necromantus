package com.necromantus.runtime;

import com.necromantus.lang.NMNativeFunc;
import com.necromantus.lang.Scope;
import com.necromantus.parser.Node;
import com.necromantus.parser.NodeType;
import com.necromantus.parser.Parser;
import com.necromantus.parser.RootNode;
import com.necromantus.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Runtime {
    private final Tokenizer t;
    private final Parser parser;

    public Runtime(String f) {
        //String f = new Scanner(System.in).nextLine();
        t = new Tokenizer(f);
        parser = new Parser(t);
    }

    private static void _printTree(Node root, int level) {
        if (root.left != null)
            if (root.left.nodeType == NodeType.VALUE) {
                System.out.println(String.join("", Collections.nCopies(level, "\t")) + root.left.value);
            } else {
                System.out.println(String.join("", Collections.nCopies(level, "\t")) + root.left.value);
                _printTree(root.left, level + 1);
            }
        if (root.right != null)
            if (root.right.nodeType == NodeType.VALUE) {
                System.out.println(String.join("", Collections.nCopies(level, "\t")) + root.right.value);
            } else {
                System.out.println(String.join("", Collections.nCopies(level, "\t")) + root.right.value);

                _printTree(root.right, level + 1);

            }
    }

    private static void printTree(Node root) {
        _printTree(root, 0);
    }

    public static void main(String[] args) {
        try {
            //new Runtime().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        Node src = parser.parseSource();
        System.out.println("Parsed source");
//        printTree(src);
//        for(String str : t.tokenEatingHistory) {
//            System.out.println(str);
//        }

        Scope scope = new Scope(null);
        Walker walker = new Walker(scope);

        walker.putNativeFunc(new NMNativeFunc("write",  (ArrayList<Object> args) -> {for(Object arg:args){System.out.print(arg);}System.out.print("\n");return null;}));

        walker.putNativeFunc(new NMNativeFunc("read",   (ArrayList<Object> args) -> new Scanner(System.in).nextLine()));

        for (Node statement : ((RootNode) src).children)
            walker.walk(statement);
        //ArrayList<Object> args = new ArrayList<>();
        //System.out.println("Staring to execute");
        //walker.getFunc("main").call(args, 0);
        //walker.getFunc("main").call(args, 0);
        //System.out.println("Result: " + walker.get("result").getValue());
    }

}
