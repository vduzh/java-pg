package by.duzh.jse.util.container.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(1, queue.poll().intValue());
        Assertions.assertEquals(2, queue.poll().intValue());
        Assertions.assertEquals(4, queue.poll().intValue());
        Assertions.assertEquals(5, queue.poll().intValue());
    }
}
