package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetTest {
    private SortedSet<Integer> set;

    @BeforeEach
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

        Assertions.assertEquals(set.first(), Integer.valueOf(4));
    }

    @Test
    public void testLast() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        Assertions.assertEquals(set.last(), Integer.valueOf(1));
    }

    @Test
    public void testHeadSet() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.headSet(1);  // -> 4, 3

        Assertions.assertEquals(2, integers.size());
        Assertions.assertEquals(4, integers.first().intValue());
        Assertions.assertEquals(3, integers.last().intValue());
    }

    @Test
    public void testHeadSetIsBigger() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.headSet(7);  // -> []

        Assertions.assertEquals(0, integers.size());
    }

    @Test
    public void testTailSet() {
        set.addAll(Arrays.asList(3, 4, 1));  // --> 4, 3, 1

        SortedSet<Integer> integers = set.tailSet(3);  // -> 3, 1

        Assertions.assertEquals(2, integers.size());
        Assertions.assertEquals(3, integers.first().intValue());
        Assertions.assertEquals(1, integers.last().intValue());
    }

    @Test
    public void testSubSet() {
        set.addAll(Arrays.asList(3, 1, 4, 0));  // --> 4, 3, 1, 0

        SortedSet<Integer> integers = set.subSet(3, 0); // -> 3, 1

        Assertions.assertEquals(2, integers.size());
        Assertions.assertEquals(3, integers.first().intValue());
        Assertions.assertEquals(1, integers.last().intValue());
    }
}