package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class IteratorTest {
    private Iterator<Integer> iterator;

    @Test
    public void testCreateIterator() {
        iterator = Arrays.asList(1, 2).iterator();
    }

    @Test
    public void testHasNext() {
        iterator = Arrays.asList(1, 2).iterator();
        Assertions.assertTrue(iterator.hasNext());
    }

    @Test
    public void testNext() {
        iterator = Arrays.asList(2).iterator();
        Assertions.assertEquals(2, iterator.next().intValue());
    }

    @Test
    public void testRemove() {
        iterator = Arrays.asList(1).iterator();
        iterator.next();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            iterator.remove();
        });
    }

    @Test
    public void testForEachRemaining() {
        iterator = Arrays.asList(1, 2, 3).iterator();

        Queue<Integer> que = new PriorityQueue();
        iterator.forEachRemaining(i -> que.add(i));

        int value = 0;
        while (!que.isEmpty()) {
            value += que.poll().intValue();
        }

        Assertions.assertEquals(6, value);
    }
}
