package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ListTest {
    private List<Integer> list;

    @Before
    public void initList() {
        list = new ArrayList<>();
    }

    @Test
    public void testInsertElementAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.add(1, -1);

        Assert.assertEquals(4, list.size());
        Assert.assertEquals(Integer.valueOf(1), list.get(0));
        Assert.assertEquals(Integer.valueOf(-1), list.get(1));
        Assert.assertEquals(Integer.valueOf(2), list.get(2));
    }

    @Test
    public void testInsertElementsAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.addAll(1, Arrays.asList(-1, -2));

        Assert.assertEquals(5, list.size());
        Assert.assertEquals(Integer.valueOf(1), list.get(0));
        Assert.assertEquals(Integer.valueOf(-1), list.get(1));
        Assert.assertEquals(Integer.valueOf(-2), list.get(2));
        Assert.assertEquals(Integer.valueOf(2), list.get(3));
    }

    @Test
    public void testGetElementAt() {
        list.addAll(Arrays.asList(1, -1, 3));

        Assert.assertEquals(Integer.valueOf(-1), list.get(1));
    }

    @Test
    public void testIndexOf() {
        list.addAll(Arrays.asList(1, -1, 3));

        Assert.assertEquals(1, list.indexOf(-1));
        Assert.assertEquals(-1, list.indexOf(-2));
    }

    @Test
    public void testLastIndexOf() {
        list.addAll(Arrays.asList(1, -1, 3, -1, 10));

        Assert.assertEquals(3, list.lastIndexOf(-1));
        Assert.assertEquals(-1, list.lastIndexOf(-2));
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
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.containsAll(Arrays.asList(-1, -2, -3)));
    }

    @Test
    public void testSetAt() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.set(1, -2);

        Assert.assertTrue(list.containsAll(Arrays.asList(1, -2, 3)));
    }

    @Test
    public void testSort() {
        list.addAll(Arrays.asList(1, 2, 3));
        list.sort((a, b) -> b - a);

        Assert.assertEquals(3, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(1, list.get(2).intValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOf() throws Exception {
        // unmodifiable list
        list = List.of(1, 2, 3);
        list.add(4);
    }

    @Test
    public void testOfWithArrays() throws Exception {
        // unmodifiable list of arrays
        Integer[] src = {1, 2, 3};
        Integer[] src2 = {4, 3};

        List<Integer[]> list = List.of(src, src2);

        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testJDK10CopyOf () {
        Collection<Integer> src = Collections.nCopies(3, 10);
        var integers = List.copyOf(src);
        Assert.assertArrayEquals(src.toArray(), integers.toArray());
    }
}
