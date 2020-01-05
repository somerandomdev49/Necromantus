package com.necromantus.token;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class TokenIdManager {
    private static int nextId = 0;

    private static final Map<String, Integer> descriptions = new HashMap<>();

    public static int getId() {
        return getId("-");
    }

    public static int getIdByDesc(String desc){
        return descriptions.getOrDefault(desc, -1);
    }

    public static int getId(String description) {
        int n = nextId++;
        descriptions.put(description, n);
        return n;
    }

    public static String getDescById(int id) {
        for (Map.Entry<String, Integer> a : descriptions.entrySet()) {
            if(a.getValue() == id) {
                return a.getKey();
            }
        }
        throw new NoSuchElementException("Id is not correct.");
    }
}
