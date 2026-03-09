package com.example.map;

import java.util.HashMap;
import java.util.Map;

public class MarkerStyleFactory {
    private static final Map<String, MarkerStyle> cache = new HashMap<>();

    public static MarkerStyle get(String shape, String color, int size, boolean filled) {
        String key = shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O");
        
        if (!cache.containsKey(key)) {
            System.out.println("[factory] Creating new style: " + key);
            cache.put(key, new MarkerStyle(shape, color, size, filled));
        }
        
        return cache.get(key);
    }

    public static int cacheSize() {
        return cache.size();
    }
}