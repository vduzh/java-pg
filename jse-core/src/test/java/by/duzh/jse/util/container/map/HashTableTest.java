package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

// Old class
// Note; extends Dictionary
public class HashTableTest {
    private Hashtable<Integer, String> hashtable;

    @Before
    public void setUp() {
        hashtable = new Hashtable<>();
    }

    @Test
    public void testCreateWithMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");

        hashtable = new Hashtable<>(map);
        Assert.assertEquals(2, hashtable.size());
    }

    @Test
    public void testClear() {
        hashtable.put(1, "one");

        hashtable.clear();

        Assert.assertTrue(hashtable.isEmpty());
    }

    @Test
    public void testContains() {
        Assert.assertFalse(hashtable.contains("one"));
        hashtable.put(1, "one");
        Assert.assertTrue(hashtable.contains("one"));
    }

    @Test
    public void testContainsKey() {
        Assert.assertFalse(hashtable.containsKey(1));
        hashtable.put(1, "one");
        Assert.assertTrue(hashtable.containsKey(1));
    }

    @Test
    public void testContainsValue() {
        Assert.assertFalse(hashtable.containsValue("one"));
        hashtable.put(1, "one");
        Assert.assertTrue(hashtable.containsValue("one"));
    }

    @Test(expected = NullPointerException.class)
    public void testPut() {
        hashtable.put(1, "one");
        hashtable.put(null, null);
    }

    @Test
    public void testGet() {
        hashtable.put(1, "one");
        Assert.assertEquals("one", hashtable.get(1));
    }

    @Test
    public void testRemove() {
        hashtable.put(1, "one");
        String s = hashtable.remove(1);

        Assert.assertEquals("one", s);
        Assert.assertTrue(hashtable.isEmpty());
    }
}
