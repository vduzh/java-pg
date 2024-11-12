package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueTest {
    private Queue<Integer> queue;

    @Before
    public void init() {
        queue = new PriorityQueue<>();
    }

    @Test
    public void testElement() {
        queue.add(1);
        Assert.assertEquals(1, queue.element().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void testElementError() {
        queue.element();
    }

    @Test
    public void testOffer() {
        Assert.assertTrue(queue.offer(1));
    }

    @Test
    public void testPeak() {
        Assert.assertNull(queue.peek());

        queue.add(1);
        Assert.assertEquals(1, queue.peek().intValue());
    }

    @Test
    public void testPoll() {
        Assert.assertNull(queue.poll());

        queue.add(1);
        Assert.assertEquals(1, queue.poll().intValue());

        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void testRemove() {
        queue.add(1);
        Assert.assertEquals(1, queue.remove().intValue());

        Assert.assertTrue(queue.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveError() {
        queue.remove();
    }
}
