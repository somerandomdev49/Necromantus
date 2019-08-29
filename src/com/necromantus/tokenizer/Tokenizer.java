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
    public String actualStr;
    private final List<TokenInfo> tokenInfos = new ArrayList<>();
    public int firstCharIndex = 1;
    public String str;
    public int lineIndex = 1;
    public ArrayList<String> tokenEatingHistory = new ArrayList<>();

    public Tokenizer(String str) {
        this.str = str;
        this.actualStr = str;
        tokenInfos.add(new TokenInfo(Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)"), TokenIdManager.getId("NAME")));
        tokenInfos.add(new TokenInfo(Pattern.compile("^((-)?[0-9]+(\\.[0-9]+)?)"), TokenIdManager.getId("NUMBER")));
        tokenInfos.add(new TokenInfo(Pattern.compile("\"[^\"]*\""), TokenIdManager.getId("STRING")));
        for (String a : "+ - * / ( ) = ; , { } [ ] == > < <= >= != & |".split(" "))
            tokenInfos.add(new TokenInfo(Pattern.compile("^(" + Pattern.quote(a) + ")"), TokenIdManager.getId("" + a)));

    }

    public Token nextToken() throws ParserException {
        Token t = seekToken();
        str = str.replaceFirst(Pattern.quote(t.value), "");
        tokenEatingHistory.add(str);
        //System.out.println(str);
        return t;
    }



    public Token seekToken() throws ParserException {

        if (str.isEmpty()) {
            return new Token(-1, "");
        }
        str = str.trim();
        str = str.replace("\t", "");
        str = str.replace("\n", "");
        str = str.replace("\r", "");
        for (TokenInfo ti : tokenInfos) {
            Matcher m = ti.pattern.matcher(str);
            if (m.lookingAt()) {
                firstCharIndex = str.indexOf(m.group(0).charAt(0));
                return new Token(ti.id, m.group(0));
            }
        }
        throw new ParserException("Could not tokenize.");
    }

    public boolean canWeLookAtNextTokenQM() {
        return !str.isEmpty();
    }
}
