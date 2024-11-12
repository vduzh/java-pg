package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TreeSetTest {
    private TreeSet<Integer> treeSet;

    @Test
    public void testCreate() {
        treeSet = new TreeSet<>();
    }

    @Test
    public void testCreateFromCollection() {
        treeSet = new TreeSet<>(Arrays.asList(4, 2, 3, 1));
        Assert.assertEquals(4, treeSet.size());
    }

    @Test
    public void testCreateWithComparator() {
        treeSet = new TreeSet<>((a, b) -> a - b);

        treeSet.add(2);
        treeSet.add(1);

        Assert.assertEquals(1, treeSet.first().intValue());
        Assert.assertEquals(2, treeSet.last().intValue());
    }

    @Test
    public void testCreateFromSortedSet() {
        SortedSet<Integer> ss = new TreeSet<>(Arrays.asList(2, 1));

        treeSet = new TreeSet<>(ss);
        Assert.assertEquals(1, treeSet.first().intValue());
        Assert.assertEquals(2, treeSet.last().intValue());
    }

    @Test
    public void testNavigableSetMethods() {
        treeSet = new TreeSet<>((a, b) -> b - a);
        treeSet.addAll(Arrays.asList(8, 4, 6, 1)); // -> 8, 6, 4, 1

        SortedSet<Integer> ss = treeSet.headSet(5);
        Assert.assertEquals(2, ss.size());

        ss = treeSet.tailSet(4);
        Assert.assertEquals(2, ss.size());
    }
}
