package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NavigableSetTest {
    private NavigableSet<Integer> set;

    @BeforeEach
    public void init() {
        set = new TreeSet<>((a, b) -> a - b);
        set.addAll(Arrays.asList(10, 1, 8, 2, 4, 5, 6)); // -> 1, 2, 4, 5, 6, 8, 10
    }

    @Test
    public void testCeiling() {
        Integer i = set.ceiling(3); // the least element in this set greater than or equal to the given element
        Assertions.assertNotNull(i);
        Assertions.assertEquals(4, i.intValue());
    }

    @Test
    public void testCeilingNull() {
        Integer i = set.ceiling(100); // null
        Assertions.assertNull(i);
    }

    @Test
    public void testDescendingIterator() {
        int value = 100;
        Iterator<Integer> iterator = set.descendingIterator();
        while (iterator.hasNext()) {
            int i = iterator.next();
            Assertions.assertTrue(i < value);
            value = i;
        }
    }

    @Test
    public void testDescendingSet() {
        set = set.descendingSet(); // -> 10, 8, 6, 5, 4, 2, 1
        Assertions.assertEquals(6, set.ceiling(7).intValue());
    }

    @Test
    public void testFloor() {
        Integer i = set.floor(7); // greatest element in this set less than or equal to the given element
        Assertions.assertNotNull(i);
        Assertions.assertEquals(6, i.intValue());
    }

    @Test
    public void testFloorNull() {
        Integer i = set.floor(-1); // Null
        Assertions.assertNull(i);
    }

    @Test
    public void testHeadSetExclusive() {
        NavigableSet<Integer> set2 = set.headSet(4, false); // -> 1, 2
        Assertions.assertEquals(2, set2.size());
        Assertions.assertEquals(1, set2.first().intValue());
        Assertions.assertEquals(2, set2.last().intValue());
    }

    @Test
    public void testHeadSetInclusive() {
        NavigableSet<Integer> set2 = set.headSet(2, true); // -> 1, 2
        Assertions.assertEquals(2, set2.size());
        Assertions.assertEquals(1, set2.first().intValue());
        Assertions.assertEquals(2, set2.last().intValue());
    }

    @Test
    public void testHigher() {
        Assertions.assertEquals(8, set.higher(7).intValue());
    }

    @Test
    public void testHigherNull() {
        Assertions.assertNull(set.higher(100));
    }

    @Test
    public void testLower() {
        Assertions.assertEquals(6, set.lower(7).intValue());
    }

    @Test
    public void testLowerNull() {
        Assertions.assertNull(set.lower(0));
    }

    @Test
    public void testPollFirst() {
        Assertions.assertEquals(1, set.pollFirst().intValue());
    }

    @Test
    public void testPollFirstNull() {
        Assertions.assertNull(new TreeSet<Integer>().pollFirst());
    }

    @Test
    public void testPollLast() {
        Assertions.assertEquals(10, set.pollLast().intValue());
    }

    @Test
    public void testSubsetInclusive() {
        NavigableSet<Integer> set1 = set.subSet(5, true, 7, true);
        Assertions.assertEquals(2, set1.size());
        Assertions.assertEquals(5, set1.first().intValue());
        Assertions.assertEquals(6, set1.last().intValue());
    }

    @Test
    public void testSubsetExclusive() {
        NavigableSet<Integer> set1 = set.subSet(4, false, 7, false);
        Assertions.assertEquals(2, set1.size());
        Assertions.assertEquals(5, set1.first().intValue());
        Assertions.assertEquals(6, set1.last().intValue());
    }

    @Test
    public void testTailSetInclusive() {
        NavigableSet<Integer> set1 = set.tailSet(7, true); //
        Assertions.assertEquals(2, set1.size());
        Assertions.assertEquals(8, set1.first().intValue());
        Assertions.assertEquals(10, set1.last().intValue());
    }

    @Test
    public void testTailSetExclusive() {
        NavigableSet<Integer> set1 = set.tailSet(6, false); //
        Assertions.assertEquals(2, set1.size());
        Assertions.assertEquals(8, set1.first().intValue());
        Assertions.assertEquals(10, set1.last().intValue());
    }
}
