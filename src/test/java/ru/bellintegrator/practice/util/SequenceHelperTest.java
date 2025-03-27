package ru.bellintegrator.practice.util;

import org.junit.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;

public class SequenceHelperTest {
    private <T> boolean equals(Comparator<T> comparator, T left, T right) {
        return comparator.compare(left, right) == 0;
    }

    @Test
    public void createArrayComparator() {
        Comparator<Integer[]> comparator = SequenceHelper.createArrayComparator(Integer::compare);
        assert equals(comparator, new Integer[] { 0, 1, 2 }, new Integer[] { 0, 1, 2 });
        assert !equals(comparator, new Integer[] { 0, 1, 2 }, new Integer[] { 0, 1, 2, 3 });
        assert !equals(comparator, new Integer[] { 0, 1, 2 }, new Integer[] { 3, 2, 1 });
    }

    @Test
    public void createIterableComparator() {
        Comparator<Iterable<Integer>> comparator = SequenceHelper.createIterableComparator(Integer::compare);
        assert equals(comparator, Arrays.asList(0, 1, 2), Arrays.asList(0, 1, 2));
        assert !equals(comparator, Arrays.asList(0, 1, 2), Arrays.asList(0, 1, 2, 3));
        assert !equals(comparator, Arrays.asList(0, 1, 2), Arrays.asList(3, 2, 1));
    }

    @Test
    public void createStreamComparator() {
        Comparator<Stream<Integer>> comparator = SequenceHelper.createStreamComparator(Integer::compare);
        assert equals(comparator, Stream.of(0, 1, 2), Stream.of(0, 1, 2));
        assert !equals(comparator, Stream.of(0, 1, 2), Stream.of(0, 1, 2, 3));
        assert !equals(comparator, Stream.of(0, 1, 2), Stream.of(3, 2, 1));
    }
}
