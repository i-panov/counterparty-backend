package ru.bellintegrator.practice.util;

public class StringPair <V> extends Pair<String, V> {
    public StringPair(String key, V value) {
        super(key, value);
    }

    public static  <V> StringPair<V> of(String key, V value) {
        return new StringPair<>(key, value);
    }
}
