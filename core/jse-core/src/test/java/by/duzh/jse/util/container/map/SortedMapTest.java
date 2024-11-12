package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SortedMapTest {
    private SortedMap<Integer, String> map;

    @Before
    public void init() {
        map = new TreeMap<>();
        map.put(3, "three");
        map.put(1, "one");
        map.put(2, "two");
    }

    @Test
    public void testComparatorDefault() {
        Comparator<? super Integer> comparator = map.comparator();
        Assert.assertNull(comparator);
    }

    @Test
    public void testComparator() {
        map = new TreeMap<>((a, b) -> b -a);
        Comparator<? super Integer> comparator = map.comparator();
        Assert.assertNotNull(comparator);
    }

    @Test
    public void testFirstKey() {
        Assert.assertEquals(1, map.firstKey().intValue());
    }

    @Test
    public void testHeadMap() {
        SortedMap<Integer, String> headMap = map.headMap(3);

        Assert.assertEquals(2, headMap.size());
        Assert.assertEquals("one", headMap.get(1));
        Assert.assertEquals("two", headMap.get(2));
    }

    @Test
    public void testLastKey() {
        Assert.assertEquals(3, map.lastKey().intValue());
    }

    @Test
    public void testSubMap() {
        map.put(4, "four");

        SortedMap<Integer, String> subMap = map.subMap(2, 4);

        Assert.assertEquals(2, subMap.size());
        Assert.assertEquals("two", subMap.get(2));
        Assert.assertEquals("three", subMap.get(3));
    }


    @Test
    public void testTailMap() {
        SortedMap<Integer, String> tailMap = map.tailMap(2);

        Assert.assertEquals(2, tailMap.size());
        Assert.assertEquals("two", tailMap.get(2));
        Assert.assertEquals("three", tailMap.get(3));
    }
}
