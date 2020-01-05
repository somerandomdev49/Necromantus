package com.necromantus;

import com.necromantus.parser.Node;
import com.necromantus.parser.NodeType;
import com.necromantus.runtime.Runtime;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

class Main {

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

    public static void printTree(Node root) {
        _printTree(root, 0);
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("Usage: necromantus <path> [-d] [-s]");
            System.exit(-1);
        }
        try {
            boolean debug = false;
            boolean silent = false;
            for(String arg : args)
                if(arg.equals("-d")) debug=true;
                else if(arg.equals("-s")) silent=true;
            byte[] content = Files.readAllBytes(Paths.get(args[0]));
            Instant start = Instant.now();
            try {
                new Runtime(debug, silent, new String(content)).run();
                System.out.println("Success");
            } catch (ParserException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            Instant done = Instant.now();
            Duration time = Duration.between(start, done);
            if(!silent)System.out.println("Necromantus interpreter completed in: " + time.getNano() / 1E+9 + "s.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void playSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
}
