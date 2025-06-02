package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class ListIteratorTest {
    private ListIterator<Integer> iterator;

    @Test
    public void testCreateListIterator() {
        iterator = Arrays.asList(1, 2, 3, 4).listIterator();
        for (int i = 1; i <= 4; i++) {
            Assertions.assertEquals(i, iterator.next().intValue());
        }
    }

    @Test
    public void testAdd() {
        iterator = new ArrayList<>(Arrays.asList(1, 2, 3, 4)).listIterator();
        iterator.add(0);
        iterator.previous();

        for (int i = 0; i <= 4; i++) {
            Assertions.assertEquals(i, iterator.next().intValue());
        }
    }

    @Test
    public void testHasPrevious() {
        iterator = Arrays.asList(1, 2, 3, 4).listIterator();

        Assertions.assertFalse(iterator.hasPrevious());

        iterator.next();
        Assertions.assertTrue(iterator.hasPrevious());
    }

    @Test
    public void testNextIndex() {
        iterator = Arrays.asList(10, 20, 30, 40).listIterator();

        Assertions.assertEquals(0, iterator.nextIndex());

        while(iterator.hasNext()) {
            iterator.next();
        }
        Assertions.assertEquals(4, iterator.nextIndex());
    }

    @Test
    public void testPreviousIndex() {
        iterator = new ArrayList<>(Arrays.asList(10, 20, 30, 40)).listIterator();

        Assertions.assertEquals(-1, iterator.previousIndex());

        while(iterator.hasNext()) {
            iterator.next();
        }
        Assertions.assertEquals(3, iterator.previousIndex());
    }

    @Test
    public void testSetByIndex() {
        iterator = new ArrayList<>(Arrays.asList(1, 2, 3, 4)).listIterator();
        iterator.next();
        iterator.next();

        iterator.set(20);

        iterator.next();
        iterator.previous();
        Assertions.assertEquals(20, iterator.previous().intValue());
    }

}
