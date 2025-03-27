package ru.bellintegrator.practice.util;

import org.slf4j.Logger;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ExceptionLogBuilder {
    private static final String DELIMITER = "-----------------------------------------------------------------------------";

    private StringBuilder builder = new StringBuilder();

    public static ExceptionLogBuilder create() {
        ExceptionLogBuilder result = new ExceptionLogBuilder();
        result.builder.append('\n');
        result.builder.append(DELIMITER);
        return result;
    }

    public ExceptionLogBuilder addParam(String param, Object value) {
        String message = String.format("\n%s: %s\n", param, value);
        builder.append(message);
        return this;
    }

    public <T> ExceptionLogBuilder addParam(String param, Stream<T> values, Function<T, String> map) {
        String value = values.map(x -> '\t' + map.apply(x)).collect(Collectors.joining("\n"));
        String message = String.format("\n%s:\n%s\n", param, value);
        builder.append(message);
        return this;
    }

    public <T> ExceptionLogBuilder addParam(String param, Iterable<T> values, Function<T, String> map) {
        return addParam(param, StreamSupport.stream(values.spliterator(), false), map);
    }

    public <T> ExceptionLogBuilder addParam(String param, T[] values, Function<T, String> map) {
        return addParam(param, Arrays.stream(values), map);
    }

    @Override
    public String toString() {
        return builder.append(DELIMITER).toString();
    }

    public static void log(Logger log, Exception e) {
        log.error(
                ExceptionLogBuilder.create()
                        .addParam("Exception message", e.getMessage())
                        .addParam("Exception stacktrace", e.getStackTrace(), StackTraceElement::toString)
                        .toString()
        );
    }
}
