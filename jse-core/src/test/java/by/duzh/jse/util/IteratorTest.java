package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void testNext() {
        iterator = Arrays.asList(2).iterator();
        Assert.assertEquals(2, iterator.next().intValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        iterator = Arrays.asList(1).iterator();
        iterator.next();
        iterator.remove();
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

        Assert.assertEquals(6, value);
    }
}
