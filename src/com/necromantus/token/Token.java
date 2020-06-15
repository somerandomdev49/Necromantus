package com.necromantus.token;

public class Token {
    public final int id;
    public final String value;
    public final int line;

    public Token(int id, String value, int line) {
        this.id = id;
        this.value = value;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
