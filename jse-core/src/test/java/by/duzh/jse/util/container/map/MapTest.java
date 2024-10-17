package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MapTest {
    private Map<Integer, String> map;

    @Before
    public void init() {
        map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
    }

    @Test
    public void testCompute() {
        // Update value under key = 2
        String newValue = map.compute(2, (i, s) -> i + "-" + s);
        Assert.assertEquals("2-two", newValue);
        Assert.assertEquals("2-two", map.get(2));
        Assert.assertEquals(3, map.size());

        // Insert a new pair: (4, "four")
        newValue = map.compute(4, (i, s) -> "four");
        Assert.assertEquals("four", newValue);
        Assert.assertEquals("four", map.get(4));
        Assert.assertEquals(4, map.size());

        // Delete value under key = 2
        newValue = map.compute(2, (i, s) -> null);
        Assert.assertNull(newValue);
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void testComputeIfAbsent() {
        // Update value under key = 2
        String newValue = map.computeIfAbsent(2, String::valueOf);
        Assert.assertEquals("two", newValue);
        Assert.assertEquals("two", map.get(2));
        Assert.assertEquals(3, map.size());

        // Insert a new pair: (4, "four")
        newValue = map.computeIfAbsent(4, String::valueOf);
        Assert.assertEquals("4", newValue);
        Assert.assertEquals("4", map.get(4));
        Assert.assertEquals(4, map.size());

        // Can not add
        newValue = map.computeIfAbsent(5, value -> null);
        Assert.assertNull(newValue);
        Assert.assertEquals(4, map.size());
    }

    @Test
    public void testComputeIfPresent() {
        // Update value under key = 2
        String newValue = map.computeIfPresent(2, (i, s) -> i + "-" + s);
        Assert.assertEquals("2-two", newValue);
        Assert.assertEquals("2-two", map.get(2));
        Assert.assertEquals(3, map.size());

        // Not found
        newValue = map.computeIfPresent(4, (i, s) -> null);
        Assert.assertNull(newValue);
        Assert.assertEquals(3, map.size());

        // Delete
        newValue = map.computeIfPresent(3, (i, s) -> null);
        Assert.assertNull(newValue);
        Assert.assertNull(map.get(3));
        Assert.assertEquals(2, map.size());
    }

    @Test
    public void testClear() {
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey() {
        Assert.assertTrue(map.containsKey(2));
        Assert.assertFalse(map.containsKey(100));
    }

    @Test
    public void testContainsValue() {
        Assert.assertTrue(map.containsValue("two"));
        Assert.assertFalse(map.containsValue("foo"));
    }

    @Test
    public void testEntrySet() {
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void testEquals() {
        HashMap<Integer, String> map2 = new HashMap<>();

        map2.put(1, "one");
        map2.put(2, "two");
        map2.put(3, "three");
        Assert.assertTrue(map.equals(map2));

        map2.remove(1);
        Assert.assertFalse(map.equals(map2));
    }

    @Test
    public void testForEach() {
        map.forEach((key, value) -> {
            // do some staff with key and value
        });
    }

    @Test
    public void testGet() {
        Assert.assertEquals("two", map.get(2));
        Assert.assertNull(map.get(5));
    }

    @Test
    public void testGetOrDefault() {
        Assert.assertEquals("two", map.getOrDefault(2, "foo"));
        Assert.assertEquals("five", map.getOrDefault(5, "five"));
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(map.isEmpty());
    }

    @Test
    public void testKeySet() {
        Set<Integer> keys = map.keySet();
        Assert.assertEquals(3, keys.size());
    }

    @Test
    public void testMerge() {
        // New pair
        String newValue = map.merge(4, "four", (s1, s2) -> "something");
        Assert.assertEquals("four", newValue);
        Assert.assertEquals("four", map.get(4));
        Assert.assertEquals(4, map.size());

        // Update pair
        newValue = map.merge(3, "foo", (s1, s2) -> s1 + " " + s2);
        Assert.assertEquals("three foo", newValue);
        Assert.assertEquals("three foo", map.get(3));
        Assert.assertEquals(4, map.size());

        // Delete pair
        newValue = map.merge(3, "foo", (s1, s2) -> null);
        Assert.assertNull(newValue);
        Assert.assertNull(map.get(3));
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void testPut() {
        String prevValue = map.put(4, "four");
        Assert.assertNull(prevValue);

        prevValue = map.put(4, "four");
        Assert.assertEquals("four", prevValue);

        Assert.assertEquals(4, map.size());
        Assert.assertEquals("four", map.get(4));
    }

    @Test
    public void testPutAll() {
        Map<Integer, String> src = new HashMap<>();
        src.put(3, "33");
        src.put(4, "44");

        map.putAll(src);

        Assert.assertEquals(4, map.size());
        Assert.assertEquals("33", map.get(3));
        Assert.assertEquals("44", map.get(4));
    }

    @Test
    public void testPutIfPresent() {
        String oldValue = map.putIfAbsent(3, "33");
        Assert.assertEquals("three", oldValue);

        oldValue = map.putIfAbsent(4, "44");
        Assert.assertNull(oldValue);

        Assert.assertEquals(4, map.size());
        Assert.assertEquals("three", map.get(3));
        Assert.assertEquals("44", map.get(4));
    }

    @Test
    public void testRemove() {
        String value = map.remove(3);
        Assert.assertEquals("three", value);

        Assert.assertEquals(2, map.size());
        Assert.assertNull(map.get(3));
    }

    @Test
    public void testRemovePair() {
        // Can't file a pair
        boolean b = map.remove(3, "33");

        Assert.assertFalse(b);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("three", map.get(3));

        // Remove found pair
        b = map.remove(3, "three");

        Assert.assertTrue(b);
        Assert.assertEquals(2, map.size());
        Assert.assertNull(map.get(3));

        // Can't pair
        b = map.remove(4, "three");
        Assert.assertFalse(b);
    }

    @Test
    public void testReplaceWithNewValue() {
        // Replace
        boolean replaced = map.replace(3, "three", "33");

        Assert.assertTrue(replaced);
        Assert.assertEquals("33", map.get(3));

        // Can't find an entry
        replaced = map.replace(3, "332", "33");

        Assert.assertFalse(replaced);
        Assert.assertEquals("33", map.get(3));
    }

    @Test
    public void testReplace() {
        // Replace
        String oldValue = map.replace(3, "33");

        Assert.assertEquals("three", oldValue);
        Assert.assertEquals("33", map.get(3));

        // Can't find an entry
        oldValue = map.replace(4, "44");

        Assert.assertNull(oldValue);
    }

    @Test
    public void testReplaceAll() {
        map.replaceAll((i, s) -> i + "" + i);

        Assert.assertEquals("11", map.get(1));
        Assert.assertEquals("22", map.get(2));
        Assert.assertEquals("33", map.get(3));
    }

    @Test
    public void testSize() {
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void testValues() {
        Collection<String> values = map.values();
        Assert.assertTrue(values.containsAll(Arrays.asList("one", "two", "three")));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOf() throws Exception {
        // unmodifiable map
        map = Map.of(1, "foo");
        map.put(4, "bar");
    }

    @Test
    public void testOfEntries() throws Exception {
        // unmodifiable map
        map = Map.ofEntries(Map.entry(1, "foo"), Map.entry(2, "bar"));
    }

    @Test
    public void testJDK10CopyOf() {
        var copy = Map.copyOf(map);
        Assert.assertEquals("two", copy.get(2));
    }
}
