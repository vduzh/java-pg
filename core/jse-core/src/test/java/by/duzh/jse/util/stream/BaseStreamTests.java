package by.duzh.jse.util.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public class BaseStreamTests {
    private final Integer[] ARRAY_SOURCE = {1, 2, 15, 16, 17, 18, 19, 20, 21, 22, 24, 56, 60, 62, 63};

    private BaseStream<Integer, Stream<Integer>> stream;

    @BeforeEach
    public void init() {
        stream = Arrays.stream(ARRAY_SOURCE);
    }

    @Test
    public void testClose() {
    }

    @Test
    public void testIsParallel() {
        Assertions.assertFalse(stream.isParallel());
        Assertions.assertTrue(stream.parallel().isParallel());
    }

    @Test
    public void testIterator() {
        Iterator<Integer> iterator = stream.iterator();
        while (iterator.hasNext()) {
            int i = iterator.next();
        }
    }

    @Test
    public void testOnClose() {
    }

    @Test
    public void testParallel() {
        Stream<Integer> parallel = stream.parallel();
        Assertions.assertTrue(parallel.isParallel());
    }

    @Test
    public void testSequential() {
        Stream<Integer> sequential = stream.parallel().sequential();
        Assertions.assertFalse(sequential.isParallel());
    }

    @Test
    public void testSpliterator() {
        Spliterator<Integer> spliterator = stream.spliterator();

        while (spliterator.tryAdvance(i -> {
            ;
        })) ;
    }

    @Test
    public void testUnordered() {
        Stream<Integer> unordered = stream.unordered();
    }
}
