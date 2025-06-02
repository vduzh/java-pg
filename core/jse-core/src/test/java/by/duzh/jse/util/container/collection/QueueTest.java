package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueTest {
    private Queue<Integer> queue;

    @BeforeEach
    public void init() {
        queue = new PriorityQueue<>();
    }

    @Test
    public void testElement() {
        queue.add(1);
        Assertions.assertEquals(1, queue.element().intValue());
    }

    @Test
    public void testElementError() {
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    public void testOffer() {
        Assertions.assertTrue(queue.offer(1));
    }

    @Test
    public void testPeak() {
        Assertions.assertNull(queue.peek());

        queue.add(1);
        Assertions.assertEquals(1, queue.peek().intValue());
    }

    @Test
    public void testPoll() {
        Assertions.assertNull(queue.poll());

        queue.add(1);
        Assertions.assertEquals(1, queue.poll().intValue());

        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    public void testRemove() {
        queue.add(1);
        Assertions.assertEquals(1, queue.remove().intValue());

        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    public void testRemoveError() {
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.remove());
    }
}
