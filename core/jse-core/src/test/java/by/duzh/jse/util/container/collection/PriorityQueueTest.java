package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueTest {
    private PriorityQueue<Integer> queue;

    @Before
    public void init() {
        queue = new PriorityQueue<>(Arrays.asList(1, 2, 3));
    }

    @Test
    public void testCreateEmptyQueueWith11Capacity() {
        queue = new PriorityQueue<>();
    }

    @Test
    public void testCreateEmptyQueueWithCapacity() {
        queue = new PriorityQueue<>(22);
    }

    @Test
    public void testCreateEmptyQueueWithCapacityAndComparator() {
        queue = new PriorityQueue<>(22, (a, b) -> a - b);
    }

    @Test
    public void testQueueMethods() {
        queue = new PriorityQueue<>(22, (a, b) -> a - b);
        queue.add(1);
        queue.add(2);

        Assert.assertEquals(1, queue.poll().intValue());
        Assert.assertEquals(2, queue.poll().intValue());
    }

    @Test
    public void testJDK11ForEach() {
        queue.forEach(i -> Assert.assertTrue(i <= 3));
    }

    @Test
    public void testJDK11RemoveAll() {
        queue.removeAll(Arrays.asList(1, 3));
        Assert.assertEquals(1, queue.size());
        Assert.assertEquals(2, queue.poll().intValue());
    }

    @Test
    public void testJDK11RemoveIf() {
        queue.removeIf(i -> i == 1 || i == 3);
        Assert.assertEquals(1, queue.size());
        Assert.assertEquals(2, queue.poll().intValue());
    }

    @Test
    public void testJDK11RetainAll() {
        queue.retainAll(Collections.singletonList(2));
        Assert.assertEquals(1, queue.size());
        Assert.assertEquals(2, queue.poll().intValue());
    }
}