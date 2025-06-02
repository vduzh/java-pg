package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

public class CollectionTest {
    private Collection<Integer> collection;

    @BeforeEach
    public void initCollection() {
        collection = new ArrayList<>();
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(collection.isEmpty());
        Assertions.assertFalse(Arrays.asList(1).isEmpty());
    }

    @Test
    public void testAddTrue() {
        boolean res = collection.add(1);
        Assertions.assertTrue(res);
        Assertions.assertEquals(collection.size(), 1);
    }

    @Test
    public void testAddAllTrue() {
        collection.add(1);

        boolean res = collection.addAll(Arrays.asList(2, 3));

        Assertions.assertTrue(res);
        Assertions.assertEquals(collection.size(), 3);
    }

    @Test
    public void testSize() {
        collection.addAll(Arrays.asList(1, 2));
        Assertions.assertEquals(collection.size(), 2);
    }

    @Test
    public void testContains() {
        collection.addAll(Arrays.asList(1, 2, 3));

        Assertions.assertTrue(collection.contains(2));
        Assertions.assertFalse(collection.contains(4));
    }

    @Test
    public void testContainsAll() {
        collection.addAll(Arrays.asList(1, 2, 3, 4));

        Assertions.assertTrue(collection.containsAll(Arrays.asList(2, 4)));
        Assertions.assertFalse(collection.containsAll(Arrays.asList(2, 5)));
    }

    @Test
    public void testClear() {
        collection.add(1);
        collection.clear();

        Assertions.assertTrue(collection.isEmpty());
    }

    @Test
    public void testRemove() {
        collection.addAll(Arrays.asList(1, 2, 3));

        Assertions.assertTrue(collection.remove(2));
        Assertions.assertFalse(collection.remove(4));
    }

    @Test
    public void testRemoveIf() {
        collection.addAll(Arrays.asList(1, 2, 6, 7, 10, 11, 12));

        boolean result = collection.removeIf(value -> value >= 5 && value <= 10);
        Assertions.assertTrue(result);
        Assertions.assertEquals(collection.size(), 4);

        result = collection.removeIf((value) -> value > 100);
        Assertions.assertFalse(result);
    }

    @Test
    public void testRetainAll() {
        collection.addAll(Arrays.asList(1, 2, 5, 6, 7, 8, 9));

        boolean changed = collection.retainAll(Arrays.asList(1, 6, 7));
        Assertions.assertTrue(changed);
        Assertions.assertEquals(collection.size(), 3);
        Assertions.assertTrue(Arrays.asList(1, 6, 7).containsAll(collection));
    }

    @Test
    public void testIterator() {
        collection.addAll(Arrays.asList(1, 2));
        Iterator<Integer> iterator = collection.iterator();
    }

    @Test
    public void testStream() {
        collection.addAll(Arrays.asList(1, 2));
        Stream<Integer> stream = collection.stream();
    }

    @Test
    public void testParallelStream() {
        collection.addAll(Arrays.asList(1, 2));
        Stream<Integer> stream = collection.parallelStream();
    }

    @Test
    public void testSpliterator() {
        collection.addAll(Arrays.asList(1, 2));
        Spliterator<Integer> spliterator = collection.spliterator();
    }

    @Test
    public void testToArray() {
        collection.addAll(Arrays.asList(1, 2));

        Object[] objects = collection.toArray();
        Assertions.assertEquals(objects[0], 1);
        Assertions.assertEquals(objects[1], 2);

        // JDK11
        Integer[] integers = collection.toArray(Integer[]::new); // Integer[]::new is: i -> new Integer[i]
        Assertions.assertArrayEquals(collection.toArray(), integers);
    }

    @Test
    public void testToArrayOfIntegers() {
        collection.addAll(Arrays.asList(1, 2));

        Integer[] integers = new Integer[2];
        Integer[] objects = collection.toArray(integers);
        Assertions.assertSame(integers, objects);
        Assertions.assertEquals(2, objects.length);
        Assertions.assertEquals(objects[0], Integer.valueOf(1));
        Assertions.assertEquals(objects[1], Integer.valueOf(2));

        integers = new Integer[1];
        objects = collection.toArray(integers);
        Assertions.assertNotSame(integers, objects);
        Assertions.assertEquals(2, objects.length);

        integers = new Integer[3];
        objects = collection.toArray(integers);
        Assertions.assertSame(integers, objects);
        Assertions.assertEquals(3, objects.length);
        Assertions.assertNull(objects[2]);
    }

    @Test
    public void testForEach() {
        collection.addAll(Arrays.asList(1, 2, 3));

        for (int i : collection) {
            Assertions.assertTrue(i > 0 && i < 4);
        }
    }
}
