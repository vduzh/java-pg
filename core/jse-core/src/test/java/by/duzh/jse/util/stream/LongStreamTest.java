package by.duzh.jse.util.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LongStreamTest {
    private LongStream stream;

    private final long[] ARRAY_SOURCE = {1, 2, 3};

    @Before
    public void init() {
        stream = LongStream.of(ARRAY_SOURCE);
    }

    @Test
    public void testRange() {
        stream = LongStream.range(1, 4);
        Assert.assertEquals(6, stream.count());
    }
}