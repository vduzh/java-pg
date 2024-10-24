package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetTest {
    private SortedSet<Integer> set;

    @Before
    public void init() {
        set = new TreeSet<>((a, b) -> b - a);
    }

    @Test
    public void testComparator() {
        Comparator<? super Integer> comparator = set.comparator();
    }

    @Test
    public void testFirst() {
        set.addAll(Arrays.asList(3, 4, 1)); // --> 4, 3, 1

        Assert.assertEquals(set.first(), Integer.valueOf(4));
    }

    @Test
    public void testLast() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        Assert.assertEquals(set.last(), Integer.valueOf(1));
    }

    @Test
    public void testHeadSet() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.headSet(1);  // -> 4, 3

        Assert.assertEquals(2, integers.size());
        Assert.assertEquals(4, integers.first().intValue());
        Assert.assertEquals(3, integers.last().intValue());
    }

    @Test
    public void testHeadSetIsBigger() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.headSet(7);  // -> []

        Assert.assertEquals(0, integers.size());
    }

    @Test
    public void testTailSet() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.tailSet(3);  // -> 3, 1

        Assert.assertEquals(2, integers.size());
        Assert.assertEquals(3, integers.first().intValue());
        Assert.assertEquals(1, integers.last().intValue());
    }

    @Test
    public void testSubSet() {
        set.addAll(Arrays.asList(3, 1, 4, 0));  // --> 4, 3, 1, 0

        SortedSet<Integer> integers = set.subSet(3, 0); // -> 3, 1

        Assert.assertEquals(2, integers.size());
        Assert.assertEquals(3, integers.first().intValue());
        Assert.assertEquals(1, integers.last().intValue());
    }
}