package ru.bellintegrator.practice.view;

import java.util.*;
import java.util.stream.*;

public class ResultView<T> {
    public final String status;
    public final T value;
    public final List<Object> errors;

    private ResultView(String status, T value, Stream<?> errors) {
        this.status = status;
        this.value = value;
        this.errors = errors.collect(Collectors.toList());
    }

    public static <T> ResultView<T> success(T result) {
        return new ResultView<>("success", result, Stream.empty());
    }

    public static ResultView<Void> success() {
        return success(null);
    }

    public static ResultView<Object> error(Stream<?> errors) {
        return new ResultView<>("error", null, errors);
    }

    public static ResultView<Object> error(Object error) {
        return error(Stream.of(error));
    }
}
