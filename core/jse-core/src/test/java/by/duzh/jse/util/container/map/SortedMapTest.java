package by.duzh.jse.util.container.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SortedMapTest {
    private SortedMap<Integer, String> map;

    @BeforeEach
    public void init() {
        map = new TreeMap<>();
        map.put(3, "three");
        map.put(1, "one");
        map.put(2, "two");
    }

    @Test
    public void testComparatorDefault() {
        Comparator<? super Integer> comparator = map.comparator();
        Assertions.assertNull(comparator);
    }

    @Test
    public void testComparator() {
        map = new TreeMap<>((a, b) -> b -a);
        Comparator<? super Integer> comparator = map.comparator();
        Assertions.assertNotNull(comparator);
    }

    @Test
    public void testFirstKey() {
        Assertions.assertEquals(1, map.firstKey().intValue());
    }

    @Test
    public void testHeadMap() {
        SortedMap<Integer, String> headMap = map.headMap(3);

        Assertions.assertEquals(2, headMap.size());
        Assertions.assertEquals("one", headMap.get(1));
        Assertions.assertEquals("two", headMap.get(2));
    }

    @Test
    public void testLastKey() {
        Assertions.assertEquals(3, map.lastKey().intValue());
    }

    @Test
    public void testSubMap() {
        map.put(4, "four");

        SortedMap<Integer, String> subMap = map.subMap(2, 4);

        Assertions.assertEquals(2, subMap.size());
        Assertions.assertEquals("two", subMap.get(2));
        Assertions.assertEquals("three", subMap.get(3));
    }

    @Test
    public void testTailMap() {
        SortedMap<Integer, String> tailMap = map.tailMap(2);

        Assertions.assertEquals(2, tailMap.size());
        Assertions.assertEquals("two", tailMap.get(2));
        Assertions.assertEquals("three", tailMap.get(3));
    }
}
