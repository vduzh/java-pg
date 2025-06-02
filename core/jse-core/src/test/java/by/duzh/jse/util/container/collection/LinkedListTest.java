package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListTest {
    private LinkedList<Integer> list;

    @Test
    public void testCreateEmptyList() {
        list = new LinkedList<>();
    }

    @Test
    public void testCreateListFromCollection() {
        list = new LinkedList<>(Arrays.asList(1, 2, 3));
        Assertions.assertEquals(3, list.size());
    }

    @Test
    public void testListMethods() {
        list = new LinkedList<>();
        list.add(0, 1);
        list.add(1, 2);
        list.set(1, 3);

        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void testQueueMethods() {
        list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.pop();

        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void testDequeMethods() {
        list = new LinkedList<>();
        list.addFirst(2);
        list.offerFirst(1);
        list.addLast(3);
        list.addLast(4);

        Assertions.assertEquals(4, list.size());
    }
}
