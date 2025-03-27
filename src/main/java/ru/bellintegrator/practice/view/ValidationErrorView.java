package ru.bellintegrator.practice.view;

public class ValidationErrorView {
    public final String field;
    public final String message;

    public ValidationErrorView(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
