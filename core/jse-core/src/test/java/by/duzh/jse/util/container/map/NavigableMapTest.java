package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;
import java.util.*;

public class NavigableMapTest {
    // Extends the SortedMap interface
    private NavigableMap<Integer, String> map;

    @Before
    public void init() {
        map = new TreeMap<>();
        map.put(1, "11");
        map.put(4, "44");
        map.put(7, "77");
        map.put(9, "99");
        map.put(12, "1212");
    }

    @Test
    public void testCeilingEntry() {
        Map.Entry<Integer, String> entry = map.ceilingEntry(4);
        Map.Entry<Integer, String> entry2 = map.ceilingEntry(5);

        Assert.assertEquals(4, entry.getKey().intValue());
        Assert.assertEquals(7, entry2.getKey().intValue());
    }

    @Test
    public void testCeilingKey() {
        Integer key = map.ceilingKey(4);
        Integer key2 = map.ceilingKey(5);

        Assert.assertEquals(4, key.intValue());
        Assert.assertEquals(7, key2.intValue());
    }

    @Test
    public void testDescendingKeySet() {
        NavigableSet<Integer> keys = map.descendingKeySet();
        Iterator<Integer> iterator = keys.iterator();
        for (int i: new int[]{12, 9, 7, 4, 1}) {
            Assert.assertEquals(i, iterator.next().intValue());
        }
    }

    @Test
    public void testDescendingMap() {
        NavigableMap<Integer, String> map2 = map.descendingMap();

        for (int i: new int[]{12, 9, 7, 4, 1}) {
            Assert.assertEquals(i, map2.pollFirstEntry().getKey().intValue());
        }
    }

    @Test
    public void testFirstEntry() {
        Map.Entry<Integer, String> entry = map.firstEntry();
        Assert.assertEquals(1,  entry.getKey().intValue());
    }

    @Test
    public void testFloorEntry() {
        Map.Entry<Integer, String> entry = map.floorEntry(4);
        Map.Entry<Integer, String> entry2 = map.floorEntry(5);
        Map.Entry<Integer, String> entry3 = map.floorEntry(0);

        Assert.assertEquals(4, entry.getKey().intValue());
        Assert.assertEquals(4, entry2.getKey().intValue());
        Assert.assertNull(entry3);
    }

    @Test
    public void testFloorKey() {
        Integer key = map.floorKey(4);
        Integer key2 = map.floorKey(5);
        Integer key3 = map.floorKey(0);

        Assert.assertEquals(4, key.intValue());
        Assert.assertEquals(4, key2.intValue());
        Assert.assertNull(key3);
    }

    @Test
    public void testHeadMap() {
        NavigableMap<Integer, String> map2 = map.headMap(4, true);

        Assert.assertEquals(2, map2.size());
        Assert.assertEquals("11", map2.get(1));
        Assert.assertEquals("44", map2.get(4));

        map2 = map.headMap(7, false);

        Assert.assertEquals(2, map2.size());
        Assert.assertEquals("11", map2.get(1));
        Assert.assertEquals("44", map2.get(4));

    }

    @Test
    public void testHigherEntry() {
        Map.Entry<Integer, String> entry = map.higherEntry(4);
        Assert.assertEquals(7, entry.getKey().intValue());
    }

    @Test
    public void testHigherKey() {
        Integer key = map.higherKey(4);
        Assert.assertEquals(7, key.intValue());
    }

    @Test
    public void testLastEntry() {
        Map.Entry<Integer, String> entry = map.lastEntry();
        Assert.assertEquals(12, entry.getKey().intValue());
    }

    @Test
    public void testLowerEntry() {
        Map.Entry<Integer, String> entry = map.lowerEntry(7);
        Assert.assertEquals(4, entry.getKey().intValue());
    }

    @Test
    public void testLowerKey() {
        Integer key = map.lowerKey(7);
        Assert.assertEquals(4, key.intValue());
    }

    @Test
    public void testNavigableKeySet() {
        NavigableSet<Integer> keys = map.navigableKeySet();
    }

    @Test
    public void testPollFirstEntry() {
        Map.Entry<Integer, String> entry = map.pollFirstEntry();
        Assert.assertEquals(1, entry.getKey().intValue());
        Assert.assertEquals(4, map.size());
    }

    @Test
    public void testPollLastEntry() {
        Map.Entry<Integer, String> entry = map.pollLastEntry();
        Assert.assertEquals(12, entry.getKey().intValue());
        Assert.assertEquals(4, map.size());
    }

    @Test
    public void testSubMap() {
        NavigableMap<Integer, String> map2 = map.subMap(4, true, 9, false);

        Assert.assertEquals(2, map2.size());
        Assert.assertEquals("44", map2.get(4));
        Assert.assertEquals("77", map2.get(7));
    }

    @Test
    public void testTailMap() {
        NavigableMap<Integer, String> map2 = map.tailMap(7,false);

        Assert.assertEquals(2, map2.size());
        Assert.assertEquals("99", map2.get(9));
        Assert.assertEquals("1212", map2.get(12));
    }
}
