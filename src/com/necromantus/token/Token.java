package com.necromantus.token;

public class Token {
    public final int id;
    public final String value;

    public Token(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
