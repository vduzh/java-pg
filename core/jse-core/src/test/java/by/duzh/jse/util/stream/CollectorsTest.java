package by.duzh.jse.util.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsTest {
    private final Integer[] ARRAY_SOURCE = {1, 2, 15, 16, 17, 18, 19, 20, 21, 22, 24, 56, 60, 62, 63};

    private Stream<Integer> stream;

    @BeforeEach
    public void init() {
        stream = Arrays.stream(ARRAY_SOURCE);
    }

    @Test
    public void testToList() {
        List<Integer> list = stream.collect(Collectors.toList());
    }

    @Test
    public void testToSet() {
        Set<Integer> set = stream.collect(Collectors.toSet());
    }

    @Test
    public void testCounting() {
        long count = stream.collect(Collectors.counting());
        Assertions.assertEquals(15, count);
    }

    @Test
    public void testAveragingInt() {
        double average = Stream.of("1", "333", "55555").collect(Collectors.averagingInt(String::length));
        Assertions.assertEquals(3.0, average, 0.001);
    }

    @Test
    public void testCollectingAndThen() {
        int count = stream.collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size()));
        Assertions.assertEquals(15, count);

        List<Integer> res = Stream.of(ARRAY_SOURCE).collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.stream().map(i -> i * 10).collect(Collectors.toList())
        ));
        Assertions.assertEquals(20, res.get(1).intValue());
    }

    @Test
    public void testJoining() {
        String s = Stream.of("1", "333", "55555").collect(Collectors.joining(","));
        Assertions.assertEquals("1,333,55555", s);
    }

    @Test
    public void testMapping() {
        long count = Stream.of("1", "333", "55555").collect(Collectors.mapping(String::length, Collectors.counting()));
        Assertions.assertEquals(3, count);
    }

    @Test
    public void testGroupingBy() {
        // Group numbers on evenness -> map: "odd" - numbers, "even" -> numbers
        Map<String, List<Integer>> oddAndEven = stream.collect(Collectors.groupingBy(i -> i % 2 == 1 ? "odd" : "even"));

        Assertions.assertEquals(2, oddAndEven.size());
        Assertions.assertEquals(6, oddAndEven.get("odd").size());
        Assertions.assertEquals(9, oddAndEven.get("even").size());
    }

    @Test
    public void testPartitioningBy() {
        // Divides into 2 groups.
        // Partitioning numbers by evenness -> map: true - odd numbers, false -> even numbers.
        Map<Boolean, List<Integer>> oddAndEven = stream.collect(Collectors.partitioningBy(i -> i % 2 == 1));

        Assertions.assertEquals(2, oddAndEven.size());
        Assertions.assertEquals(6, oddAndEven.get(true).size());
        Assertions.assertEquals(9, oddAndEven.get(false).size());
    }

    @Test
    public void testJDK9Filtering() {
        List<Integer> res = stream.collect(Collectors.filtering(i -> i <= 15, Collectors.toList()));
        Assertions.assertEquals("[1, 2, 15]", res.toString());
    }

    @Test
    public void testJDK9FlatMapping() {
        List<Integer> res = Stream.of("2", "3").collect(Collectors.flatMapping(
                s -> Stream.iterate(1, i -> i <= Integer.parseInt(s), i -> i + 1),
                Collectors.toList()));
        Assertions.assertEquals("[1, 2, 1, 2, 3]", res.toString());
    }

    @Test
    public void testJDK10ToUnmodifiableList () {
        var list = stream.collect(Collectors.toUnmodifiableList());
    }

    @Test
    public void testJDK10ToUnmodifiableSet () {
        var set = stream.collect(Collectors.toUnmodifiableSet());
    }

    @Test
    public void testJDK10ToUnmodifiableMap () {
        var map = Stream.of("1", "333").collect(Collectors.toUnmodifiableMap(Integer::parseInt, String::length));
        Assertions.assertEquals(1, map.get(1).intValue());
        Assertions.assertEquals(3, map.get(333).intValue());

        map = Stream.of("1", "333", "333").collect(Collectors.toUnmodifiableMap(Integer::parseInt, String::length, Integer::sum));
        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals(1, map.get(1).intValue());
        Assertions.assertEquals(6, map.get(333).intValue());
    }
}