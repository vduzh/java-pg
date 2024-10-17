package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

//TODO: have a look at this class in details
public class SpliteratorTest {
    private Spliterator<Integer> spliterator;

    @Before
    public void init() {
        spliterator = new ArrayList(Arrays.asList(1, 2, 3, 4)).spliterator();
    }

    @Test
    public void testCharacteristics() {
        // TODO: What is it?
        int characteristics = spliterator.characteristics();
        System.out.println(characteristics);
    }

    @Test
    public void testEstimatedSize() {
        long estimateSize = spliterator.estimateSize();
        Assert.assertEquals(4, estimateSize);
    }

    @Test
    public void testForEachRemaining() {
        List<Integer> integers = new ArrayList<>();

        spliterator.forEachRemaining(integers::add);

        int result = 0;
        for (int i : integers) {
            result += i;
        }
        Assert.assertEquals(10, result);
    }

    @Test
    public void testGetExactSizeIfKnown() {
        long size = spliterator.getExactSizeIfKnown();
        Assert.assertEquals(4, size);
    }

    @Test
    public void testTryAdvance() {
        Queue<Integer> queue = new PriorityQueue<>();

        for (int i = 1; i <= 4; i++) {
            boolean b = spliterator.tryAdvance(queue::add);
            Assert.assertTrue(b);
            Assert.assertEquals(1, queue.size());
            Assert.assertEquals(i, queue.poll().intValue());
        }

        boolean b = spliterator.tryAdvance(queue::add);
        Assert.assertFalse(b);
    }

    //TODO: add tests for multi-threading
}
