package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class NavigableSetTest {
    private NavigableSet<Integer> set;

    @Before
    public void init() {
        set = new TreeSet<>((a, b) -> a - b);
        set.addAll(Arrays.asList(10, 1, 8, 2, 4, 5, 6)); // -> 1, 2, 4, 5, 6, 8, 10
    }

    @Test
    public void testCeiling() {
        Integer i = set.ceiling(3); // the least element in this set greater than or equal to the given element
        Assert.assertNotNull(i);
        Assert.assertEquals(4, i.intValue());
    }

    @Test
    public void testCeilingNull() {
        Integer i = set.ceiling(100); // null
        Assert.assertNull(i);
    }

    @Test
    public void testDescendingIterator() {
        int value = 100;
        Iterator<Integer> iterator = set.descendingIterator();
        while (iterator.hasNext()) {
            int i = iterator.next();
            Assert.assertTrue(i < value);
            value = i;
        }
    }

    @Test
    public void testDescendingSet() {
        set = set.descendingSet(); // -> 10, 8, 6, 5, 4, 2, 1
        Assert.assertEquals(6, set.ceiling(7).intValue());
    }

    @Test
    public void testFloor() {
        Integer i = set.floor(7); // greatest element in this set less than or equal to the given element
        Assert.assertNotNull(i);
        Assert.assertEquals(6, i.intValue());
    }

    @Test
    public void testFloorNull() {
        Integer i = set.floor(-1); // Null
        Assert.assertNull(i);
    }

    @Test
    public void testHeadSetExclusive() {
        NavigableSet<Integer> set2 = set.headSet( 4, false); // -> 1, 2
        Assert.assertEquals(2, set2.size());
        Assert.assertEquals(1, set2.first().intValue());
        Assert.assertEquals(2, set2.last().intValue());
    }

    @Test
    public void testHeadSetInclusive() {
        NavigableSet<Integer> set2 = set.headSet( 2, true); // -> 1, 2
        Assert.assertEquals(2, set2.size());
        Assert.assertEquals(1, set2.first().intValue());
        Assert.assertEquals(2, set2.last().intValue());
    }

    @Test
    public void testHigher() {
        Assert.assertEquals(8, set.higher(7).intValue());
    }

    @Test
    public void testHigherNull() {
        Assert.assertNull(set.higher(100));
    }

    @Test
    public void testLower() {
        Assert.assertEquals(6, set.lower(7).intValue());
    }

    @Test
    public void testLowerNull() {
        Assert.assertNull(set.lower(0));
    }

    @Test
    public void testPollFirst() {
        Assert.assertEquals(1, set.pollFirst().intValue());
    }

    @Test
    public void testPollFirstNull() {
        Assert.assertNull(new TreeSet<Integer>().pollFirst());
    }

    @Test
    public void testPollLast() {
        Assert.assertEquals(10, set.pollLast().intValue());
    }


    @Test
    public void testSubsetInclusive() {
        NavigableSet<Integer> set1 = set.subSet(5, true, 7, true);
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(5, set1.first().intValue());
        Assert.assertEquals(6, set1.last().intValue());
    }

    @Test
    public void testSubsetExclusive() {
        NavigableSet<Integer> set1 = set.subSet(4, false, 7, false);
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(5, set1.first().intValue());
        Assert.assertEquals(6, set1.last().intValue());
    }

    @Test
    public void testTailSetInclusive() {
        NavigableSet<Integer> set1 = set.tailSet(7, true); //
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(8, set1.first().intValue());
        Assert.assertEquals(10, set1.last().intValue());
    }

    @Test
    public void testTailSetExclusive() {
        NavigableSet<Integer> set1 = set.tailSet(6, false); //
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(8, set1.first().intValue());
        Assert.assertEquals(10, set1.last().intValue());
    }
}
