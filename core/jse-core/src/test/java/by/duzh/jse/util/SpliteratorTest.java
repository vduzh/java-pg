package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

//TODO: have a look at this class in details
public class SpliteratorTest {
    private Spliterator<Integer> spliterator;

    @BeforeEach
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
        Assertions.assertEquals(4, estimateSize);
    }

    @Test
    public void testForEachRemaining() {
        List<Integer> integers = new ArrayList<>();

        spliterator.forEachRemaining(integers::add);

        int result = 0;
        for (int i : integers) {
            result += i;
        }
        Assertions.assertEquals(10, result);
    }

    @Test
    public void testGetExactSizeIfKnown() {
        long size = spliterator.getExactSizeIfKnown();
        Assertions.assertEquals(4, size);
    }

    @Test
    public void testTryAdvance() {
        Queue<Integer> queue = new PriorityQueue<>();

        for (int i = 1; i <= 4; i++) {
            boolean b = spliterator.tryAdvance(queue::add);
            Assertions.assertTrue(b);
            Assertions.assertEquals(1, queue.size());
            Assertions.assertEquals(i, queue.poll().intValue());
        }

        boolean b = spliterator.tryAdvance(queue::add);
        Assertions.assertFalse(b);
    }

    //TODO: add tests for multi-threading
}
