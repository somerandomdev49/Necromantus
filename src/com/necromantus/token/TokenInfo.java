package com.necromantus.token;

import java.util.regex.Pattern;

public class TokenInfo {
    public final Pattern pattern;
    public final int id;

    public TokenInfo(Pattern pattern, int id) {
        this.pattern = pattern;
        this.id = id;
    }
}
