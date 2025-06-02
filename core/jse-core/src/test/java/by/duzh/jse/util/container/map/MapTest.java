package by.duzh.jse.util.container.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MapTest {
    private Map<Integer, String> map;

    @BeforeEach
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
        Assertions.assertEquals("2-two", newValue);
        Assertions.assertEquals("2-two", map.get(2));
        Assertions.assertEquals(3, map.size());

        // Insert a new pair: (4, "four")
        newValue = map.compute(4, (i, s) -> "four");
        Assertions.assertEquals("four", newValue);
        Assertions.assertEquals("four", map.get(4));
        Assertions.assertEquals(4, map.size());

        // Delete value under key = 2
        newValue = map.compute(2, (i, s) -> null);
        Assertions.assertNull(newValue);
        Assertions.assertEquals(3, map.size());
    }

    @Test
    public void testComputeIfAbsent() {
        // Update value under key = 2
        String newValue = map.computeIfAbsent(2, String::valueOf);
        Assertions.assertEquals("two", newValue);
        Assertions.assertEquals("two", map.get(2));
        Assertions.assertEquals(3, map.size());

        // Insert a new pair: (4, "four")
        newValue = map.computeIfAbsent(4, String::valueOf);
        Assertions.assertEquals("4", newValue);
        Assertions.assertEquals("4", map.get(4));
        Assertions.assertEquals(4, map.size());

        // Can not add
        newValue = map.computeIfAbsent(5, value -> null);
        Assertions.assertNull(newValue);
        Assertions.assertEquals(4, map.size());
    }

    @Test
    public void testComputeIfPresent() {
        // Update value under key = 2
        String newValue = map.computeIfPresent(2, (i, s) -> i + "-" + s);
        Assertions.assertEquals("2-two", newValue);
        Assertions.assertEquals("2-two", map.get(2));
        Assertions.assertEquals(3, map.size());

        // Not found
        newValue = map.computeIfPresent(4, (i, s) -> null);
        Assertions.assertNull(newValue);
        Assertions.assertEquals(3, map.size());

        // Delete
        newValue = map.computeIfPresent(3, (i, s) -> null);
        Assertions.assertNull(newValue);
        Assertions.assertNull(map.get(3));
        Assertions.assertEquals(2, map.size());
    }

    @Test
    public void testClear() {
        map.clear();
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey() {
        Assertions.assertTrue(map.containsKey(2));
        Assertions.assertFalse(map.containsKey(100));
    }

    @Test
    public void testContainsValue() {
        Assertions.assertTrue(map.containsValue("two"));
        Assertions.assertFalse(map.containsValue("foo"));
    }

    @Test
    public void testEntrySet() {
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        Assertions.assertEquals(3, set.size());
    }

    @Test
    public void testEquals() {
        HashMap<Integer, String> map2 = new HashMap<>();

        map2.put(1, "one");
        map2.put(2, "two");
        map2.put(3, "three");
        Assertions.assertTrue(map.equals(map2));

        map2.remove(1);
        Assertions.assertFalse(map.equals(map2));
    }

    @Test
    public void testForEach() {
        map.forEach((key, value) -> {
            // do some staff with key and value
        });
    }

    @Test
    public void testGet() {
        Assertions.assertEquals("two", map.get(2));
        Assertions.assertNull(map.get(5));
    }

    @Test
    public void testGetOrDefault() {
        Assertions.assertEquals("two", map.getOrDefault(2, "foo"));
        Assertions.assertEquals("five", map.getOrDefault(5, "five"));
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertFalse(map.isEmpty());
    }

    @Test
    public void testKeySet() {
        Set<Integer> keys = map.keySet();
        Assertions.assertEquals(3, keys.size());
    }

    @Test
    public void testMerge() {
        // New pair
        String newValue = map.merge(4, "four", (s1, s2) -> "something");
        Assertions.assertEquals("four", newValue);
        Assertions.assertEquals("four", map.get(4));
        Assertions.assertEquals(4, map.size());

        // Update pair
        newValue = map.merge(3, "foo", (s1, s2) -> s1 + " " + s2);
        Assertions.assertEquals("three foo", newValue);
        Assertions.assertEquals("three foo", map.get(3));
        Assertions.assertEquals(4, map.size());

        // Delete pair
        newValue = map.merge(3, "foo", (s1, s2) -> null);
        Assertions.assertNull(newValue);
        Assertions.assertNull(map.get(3));
        Assertions.assertEquals(3, map.size());
    }

    @Test
    public void testPut() {
        String prevValue = map.put(4, "four");
        Assertions.assertNull(prevValue);

        prevValue = map.put(4, "four");
        Assertions.assertEquals("four", prevValue);

        Assertions.assertEquals(4, map.size());
        Assertions.assertEquals("four", map.get(4));
    }

    @Test
    public void testPutAll() {
        Map<Integer, String> src = new HashMap<>();
        src.put(3, "33");
        src.put(4, "44");

        map.putAll(src);

        Assertions.assertEquals(4, map.size());
        Assertions.assertEquals("33", map.get(3));
        Assertions.assertEquals("44", map.get(4));
    }

    @Test
    public void testPutIfPresent() {
        String oldValue = map.putIfAbsent(3, "33");
        Assertions.assertEquals("three", oldValue);

        oldValue = map.putIfAbsent(4, "44");
        Assertions.assertNull(oldValue);

        Assertions.assertEquals(4, map.size());
        Assertions.assertEquals("three", map.get(3));
        Assertions.assertEquals("44", map.get(4));
    }

    @Test
    public void testRemove() {
        String value = map.remove(3);
        Assertions.assertEquals("three", value);

        Assertions.assertEquals(2, map.size());
        Assertions.assertNull(map.get(3));
    }

    @Test
    public void testRemovePair() {
        // Can't file a pair
        boolean b = map.remove(3, "33");

        Assertions.assertFalse(b);
        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("three", map.get(3));

        // Remove found pair
        b = map.remove(3, "three");

        Assertions.assertTrue(b);
        Assertions.assertEquals(2, map.size());
        Assertions.assertNull(map.get(3));

        // Can't pair
        b = map.remove(4, "three");
        Assertions.assertFalse(b);
    }

    @Test
    public void testReplaceWithNewValue() {
        // Replace
        boolean replaced = map.replace(3, "three", "33");

        Assertions.assertTrue(replaced);
        Assertions.assertEquals("33", map.get(3));

        // Can't find an entry
        replaced = map.replace(3, "332", "33");

        Assertions.assertFalse(replaced);
        Assertions.assertEquals("33", map.get(3));
    }

    @Test
    public void testReplace() {
        // Replace
        String oldValue = map.replace(3, "33");

        Assertions.assertEquals("three", oldValue);
        Assertions.assertEquals("33", map.get(3));

        // Can't find an entry
        oldValue = map.replace(4, "44");

        Assertions.assertNull(oldValue);
    }

    @Test
    public void testReplaceAll() {
        map.replaceAll((i, s) -> i + "" + i);

        Assertions.assertEquals("11", map.get(1));
        Assertions.assertEquals("22", map.get(2));
        Assertions.assertEquals("33", map.get(3));
    }

    @Test
    public void testSize() {
        Assertions.assertEquals(3, map.size());
    }

    @Test
    public void testValues() {
        Collection<String> values = map.values();
        Assertions.assertTrue(values.containsAll(Arrays.asList("one", "two", "three")));
    }

    @Test
    public void testOf() {
        Map<Integer, String> immutableMap = Map.of(1, "one", 2, "two", 3, "three");
        Assertions.assertEquals(3, immutableMap.size());
        Assertions.assertEquals("one", immutableMap.get(1));
        Assertions.assertEquals("two", immutableMap.get(2));
        Assertions.assertEquals("three", immutableMap.get(3));
        
        // Проверяем, что карта действительно неизменяемая
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableMap.put(4, "four"));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableMap.remove(1));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableMap.clear());
    }

    @Test
    public void testOfEntries() {
        Map.Entry<Integer, String> entry = new AbstractMap.SimpleEntry<>(1, "one");
        Map<Integer, String> map = Map.ofEntries(entry);
        Assertions.assertEquals(1, map.size());
    }

    @Test
    public void testJDK10CopyOf() {
        var copy = Map.copyOf(map);
        Assertions.assertEquals("two", copy.get(2));
    }
}
