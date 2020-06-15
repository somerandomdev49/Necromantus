package com.necromantus.tokenizer;

import com.necromantus.ParserException;
import com.necromantus.token.Token;
import com.necromantus.token.TokenIdManager;
import com.necromantus.token.TokenInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private String actualStr;
    public ArrayList<TokenInfo> tokenInfos;
    private int firstCharIndex = 1;
    public String str;
    private int index = -1;
    private int lineIndex = 1;
    public ArrayList<String> tokenEatingHistory = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();
    private boolean debug, silent;

    public Tokenizer(boolean debug, boolean silent, String str, ArrayList<TokenInfo> tokenInfos) {
        this.str = str;
        this.actualStr = str;
        this.tokenInfos = tokenInfos;
        this.debug = debug;
        this.silent = silent;
    }

    public Token nextToken() {
//        if(index==-1)
//            System.out.println(str.trim().replace("\n", "").replace("\t", "").replace("\r", ""));
//        else
//            System.out.println(tokenEatingHistory.get(index));
        return tokens.get(++index);
    }

    private void trim() {
        while(Character.isSpaceChar(str.charAt(0))) {
            if(str.charAt(0) == '\n') lineIndex++;
            str = str.substring(1);
        }
    }

    public void tokenize() throws ParserException {
        outer:
        while(!str.isEmpty()) {
            trim();
            str = str.replace("\t", "");
            str = str.replace("\n", "");
            str = str.replace("\r", "");
            for (TokenInfo ti : tokenInfos) {
                Matcher m = ti.pattern.matcher(str);
                if (m.lookingAt()) {
                    firstCharIndex = str.indexOf(m.group(0).charAt(0));
                    str = str.replaceFirst(Pattern.quote(m.group(0)), "");
                    tokenEatingHistory.add(str);
                    tokens.add(new Token(ti.id, m.group(0), lineIndex));
                    continue outer;
                }
            }
            throw new ParserException("Could not tokenize.");
        }
    }

    public Token seekToken() {
        return tokens.get(index+1);
    }

    public Token seekToken(int o) {
        return tokens.get(index+1+o);
    }

    public boolean canWeLookAtNextTokenQM() {
        return index+1 < tokens.size();
    }
}
