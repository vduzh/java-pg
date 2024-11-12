package by.duzh.jse.util.container.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;

public class ArrayDequeTest {
    private ArrayDeque<Integer> queue;

    @Test
    public void testCreateEmptyWith16Capacity() {
        queue = new ArrayDeque<>();
    }

    @Test
    public void testDequeMethods() {
        queue = new ArrayDeque<>();
        queue.addFirst(2);
        queue.addFirst(1);
        queue.addLast(4);
        queue.addLast(5);

        Assert.assertEquals(1, queue.poll().intValue());
        Assert.assertEquals(2, queue.poll().intValue());
        Assert.assertEquals(4, queue.poll().intValue());
        Assert.assertEquals(5, queue.poll().intValue());
    }
}
