package by.duzh.jse.util.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public class BaseStreamTests {
    private final Integer[] ARRAY_SOURCE = {1, 2, 15, 16, 17, 18, 19, 20, 21, 22, 24, 56, 60, 62, 63};

    private BaseStream<Integer, Stream<Integer>> stream;

    @Before
    public void init() {
        stream = Arrays.stream(ARRAY_SOURCE);
    }

    @Test
    public void testClose() {
    }

    @Test
    public void testIsParallel() {
        Assert.assertFalse(stream.isParallel());
        Assert.assertTrue(stream.parallel().isParallel());
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
        Assert.assertTrue(stream.parallel().isParallel());
    }

    @Test
    public void testSequential() {
        Assert.assertFalse(stream.parallel().sequential().isParallel());
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
