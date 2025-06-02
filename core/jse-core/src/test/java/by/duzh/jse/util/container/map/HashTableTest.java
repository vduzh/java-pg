package by.duzh.jse.util.container.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

// Old class
// Note; extends Dictionary
public class HashTableTest {
    private Hashtable<Integer, String> hashtable;

    @BeforeEach
    public void setUp() {
        hashtable = new Hashtable<>();
    }

    @Test
    public void testCreateWithMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");

        hashtable = new Hashtable<>(map);
        Assertions.assertEquals(2, hashtable.size());
    }

    @Test
    public void testClear() {
        hashtable.put(1, "one");

        hashtable.clear();

        Assertions.assertTrue(hashtable.isEmpty());
    }

    @Test
    public void testContains() {
        Assertions.assertFalse(hashtable.contains("one"));
        hashtable.put(1, "one");
        Assertions.assertTrue(hashtable.contains("one"));
    }

    @Test
    public void testContainsKey() {
        Assertions.assertFalse(hashtable.containsKey(1));
        hashtable.put(1, "one");
        Assertions.assertTrue(hashtable.containsKey(1));
    }

    @Test
    public void testContainsValue() {
        Assertions.assertFalse(hashtable.containsValue("one"));
        hashtable.put(1, "one");
        Assertions.assertTrue(hashtable.containsValue("one"));
    }

    @Test
    public void testPut() {
        hashtable.put(1, "one");
        Assertions.assertThrows(NullPointerException.class, () -> {
            hashtable.put(null, null);
        });
    }

    @Test
    public void testGet() {
        hashtable.put(1, "one");
        Assertions.assertEquals("one", hashtable.get(1));
    }

    @Test
    public void testRemove() {
        hashtable.put(1, "one");
        String s = hashtable.remove(1);

        Assertions.assertEquals("one", s);
        Assertions.assertTrue(hashtable.isEmpty());
    }
}
