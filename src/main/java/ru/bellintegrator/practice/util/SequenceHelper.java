package ru.bellintegrator.practice.util;

import java.util.*;
import java.util.stream.Stream;

public class SequenceHelper {
    public static <T> Comparator<T[]> createArrayComparator(Comparator<T> itemComparator) {
        return (left, right) -> compare(createIterator(left), createIterator(right), itemComparator);
    }

    public static <T> Comparator<Iterable<T>> createIterableComparator(Comparator<T> itemComparator) {
        return (left, right) -> compare(left.iterator(), right.iterator(), itemComparator);
    }

    public static <T> Comparator<Stream<T>> createStreamComparator(Comparator<T> itemComparator) {
        return (left, right) -> compare(left.iterator(), right.iterator(), itemComparator);
    }

    public static <T> Iterator<T> createIterator(T[] arr) {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    public static <T> List<T> asMutableList(T ... items) {
        List<T> result = new ArrayList<>();

        for (T item : items)
            result.add(item);

        return result;
    }

    private static <T> int compare(Iterator<T> left, Iterator<T> right, Comparator<T> comparator) {
        int sum = 0;

        for (; left.hasNext() && right.hasNext();) {
            sum += comparator.compare(left.next(), right.next());
        }

        while (left.hasNext()) {
            left.next();
            sum--;
        }

        while (right.hasNext()) {
            right.next();
            sum++;
        }

        return Integer.compare(sum, 0);
    }
}
