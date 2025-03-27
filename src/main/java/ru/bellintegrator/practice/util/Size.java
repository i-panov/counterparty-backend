package ru.bellintegrator.practice.util;

public class Size {
    public int width = 0;
    public int height = 0;

    private Size() {
    }

    public static Size of(int width, int height) {
        Size result = new Size();
        result.width = width;
        result.height = height;
        return result;
    }

    public static Size empty() {
        return new Size();
    }
}
