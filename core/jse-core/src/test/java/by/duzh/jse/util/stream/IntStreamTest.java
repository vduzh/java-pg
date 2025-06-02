package by.duzh.jse.util.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntStreamTest {
    private IntStream stream;

    private final int[] ARRAY_SOURCE = {1, 2, 3};

    @BeforeEach
    public void init() {
        stream = IntStream.of(ARRAY_SOURCE);
    }

    @Test
    public void testMin() {
        OptionalInt min = stream.min();
        Assertions.assertTrue(min.isPresent());
        Assertions.assertEquals(1, min.getAsInt());
    }

    @Test
    public void testSum() {
        int sum = stream.sum();
        Assertions.assertEquals(6, sum);
    }

    @Test
    public void testAverage() {
        OptionalDouble average = stream.average();
        Assertions.assertEquals(2.0, average.getAsDouble(), 0.01);
    }

    @Test
    public void testBoxed() {
        List<Integer> integers = Stream.of("One", "Two")
                .mapToInt(String::length)
                .boxed()
                .collect(Collectors.toList());
    }

    @Test
    public void testSummaryStatistics() {
        IntSummaryStatistics statistics = stream.summaryStatistics();

        Assertions.assertEquals( 6, statistics.getSum());
        Assertions.assertEquals( 3, statistics.getCount());
    }
}