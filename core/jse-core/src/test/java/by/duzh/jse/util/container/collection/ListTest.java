package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ListTest {
    private List<Integer> list;

    @BeforeEach
    public void initList() {
        list = new ArrayList<>();
    }

    @Test
    public void testInsertElementAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.add(1, -1);

        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(-1), list.get(1));
        Assertions.assertEquals(Integer.valueOf(2), list.get(2));
    }

    @Test
    public void testInsertElementsAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.addAll(1, Arrays.asList(-1, -2));

        Assertions.assertEquals(5, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(-1), list.get(1));
        Assertions.assertEquals(Integer.valueOf(-2), list.get(2));
        Assertions.assertEquals(Integer.valueOf(2), list.get(3));
    }

    @Test
    public void testGetElementAt() {
        list.addAll(Arrays.asList(1, -1, 3));

        Assertions.assertEquals(Integer.valueOf(-1), list.get(1));
    }

    @Test
    public void testIndexOf() {
        list.addAll(Arrays.asList(1, -1, 3));

        Assertions.assertEquals(1, list.indexOf(-1));
        Assertions.assertEquals(-1, list.indexOf(-2));
    }

    @Test
    public void testLastIndexOf() {
        list.addAll(Arrays.asList(1, -1, 3, -1, 10));

        Assertions.assertEquals(3, list.lastIndexOf(-1));
        Assertions.assertEquals(-1, list.lastIndexOf(-2));
    }

    @Test
    public void testListIterator() {
        list.addAll(Arrays.asList(1, -1, 3, -1, 10));

        ListIterator<Integer> iterator = list.listIterator();
    }

    @Test
    public void testListIteratorWithPosition() {
        list.addAll(Arrays.asList(1, -1, 3, -1, 10));

        ListIterator<Integer> iterator = list.listIterator(2);
    }

    @Test
    public void testReplaceAll() {
        list.addAll(Arrays.asList(1, 2, 3));

        list.replaceAll(value -> value * -1);
        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.containsAll(Arrays.asList(-1, -2, -3)));
    }

    @Test
    public void testSetAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.set(1, -2);

        Assertions.assertTrue(list.containsAll(Arrays.asList(1, -2, 3)));
    }

    @Test
    public void testSort() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.sort((a, b) -> b - a);

        Assertions.assertEquals(3, list.get(0).intValue());
        Assertions.assertEquals(2, list.get(1).intValue());
        Assertions.assertEquals(1, list.get(2).intValue());
    }

    @Test
    public void testOf() {
        // unmodifiable list
        list = List.of(1, 2, 3);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add(4));
    }

    @Test
    public void testOfWithArrays() {
        // unmodifiable list of arrays
        Integer[] src = {1, 2, 3};
        Integer[] src2 = {4, 3};

        List<Integer[]> list = List.of(src, src2);

        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void testJDK10CopyOf() {
        Collection<Integer> src = Collections.nCopies(3, 10);
        List<Integer> integers = List.copyOf(src);
        Assertions.assertArrayEquals(src.toArray(), integers.toArray());
    }
}
