package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class CollectionTest {
    private Collection<Integer> collection;

    @Before
    public void initCollection() {
        collection = new ArrayList<>();
    }

    @Test
    public void testAddTrue() {
        boolean res = collection.add(1);
        Assert.assertTrue(res);
        Assert.assertEquals(collection.size(), 1);
    }

    @Test
    public void testAddAllTrue() {
        collection.add(1);

        boolean res = collection.addAll(Arrays.asList(2, 3));

        Assert.assertTrue(res);
        Assert.assertEquals(collection.size(), 3);
    }

    @Test
    public void testClear() {
        collection.add(1);
        collection.clear();

        Assert.assertEquals(collection.size(), 0);
    }

    @Test
    public void testContains() {
        collection.addAll(Arrays.asList(1, 2, 3));

        Assert.assertTrue(collection.contains(2));
        Assert.assertFalse(collection.contains(4));
    }

    @Test
    public void testContainsAll() {
        collection.addAll(Arrays.asList(1, 2, 3, 4));

        Assert.assertTrue(collection.containsAll(Arrays.asList(2, 4)));
        Assert.assertFalse(collection.containsAll(Arrays.asList(2, 5)));
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(collection.isEmpty());
        Assert.assertFalse(Arrays.asList(1).isEmpty());
    }

    @Test
    public void testIterator() {
        collection.addAll(Arrays.asList(1, 2));
        Iterator<Integer> iterator = collection.iterator();
    }

    @Test
    public void testParallelStream() {
        collection.addAll(Arrays.asList(1, 2));
        Stream<Integer> stream = collection.parallelStream();
    }

    @Test
    public void testRemove() {
        collection.addAll(Arrays.asList(1, 2, 3));

        Assert.assertTrue(collection.remove(2));
        Assert.assertFalse(collection.remove(4));
    }

    @Test
    public void testRemoveIf() {
        collection.addAll(Arrays.asList(1, 2, 6, 7, 10, 11, 12));

        boolean result = collection.removeIf((value) -> value >=5 && value <= 10);
        Assert.assertTrue(result);
        Assert.assertEquals(collection.size(), 4);

        result = collection.removeIf((value) -> value > 100);
        Assert.assertFalse(result);
    }

    @Test
    public void testRetainAll() {
        collection.addAll(Arrays.asList(1, 2, 5, 6, 7, 8, 9));

        boolean changed = collection.retainAll(Arrays.asList(1, 6, 7));
        Assert.assertTrue(changed);
        Assert.assertEquals(collection.size(),3);
        Assert.assertTrue(Arrays.asList(1, 6, 7).containsAll(collection));
    }

    @Test
    public void testSize() {
        collection.addAll(Arrays.asList(1, 2));
        Assert.assertEquals(collection.size(),2);
    }

    @Test
    public void testSpliterator() {
        collection.addAll(Arrays.asList(1, 2));
        Spliterator<Integer> spliterator = collection.spliterator();
    }

    @Test
    public void testStream() {
        collection.addAll(Arrays.asList(1, 2));
        Stream<Integer> stream = collection.stream();
    }

    @Test
    public void testToArray() {
        collection.addAll(Arrays.asList(1, 2));

        Object[] objects = collection.toArray();
        Assert.assertEquals(objects[0], 1);
        Assert.assertEquals(objects[1], 2);

        // JDK11
        Integer[] integers = collection.toArray(Integer[]::new); // Integer[]::new = i -> new Integer[i]
        Assert.assertArrayEquals(collection.toArray(), integers);
    }

    @Test
    public void testToArrayOfIntegers() {
        collection.addAll(Arrays.asList(1, 2));

        Integer[] integers = new Integer[2];
        Integer[] objects = collection.toArray(integers);
        Assert.assertSame(integers, objects);
        Assert.assertEquals(2, objects.length);
        Assert.assertEquals(objects[0], Integer.valueOf(1));
        Assert.assertEquals(objects[1], Integer.valueOf(2));

        integers = new Integer[1];
        objects = collection.toArray(integers);
        Assert.assertNotSame(integers, objects);
        Assert.assertEquals(2, objects.length);

        integers = new Integer[3];
        objects = collection.toArray(integers);
        Assert.assertSame(integers, objects);
        Assert.assertEquals(3, objects.length);
        Assert.assertNull(objects[2]);
    }

    @Test
    public void testForEach() {
        collection.addAll(Arrays.asList(1, 2, 3));

        for (int i: collection) {
            Assert.assertTrue(i > 0 && i < 4);
        }
    }

}
