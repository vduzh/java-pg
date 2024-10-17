package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

// Old class
// NOTE: it is an abstract class!!!
public class DictionaryTest {
    private Dictionary<Integer, String> dictionary;

    @Before
    public void setUp() {
        dictionary = new Hashtable<>();
    }

    @Test
    public void testPut() {
        String s = dictionary.put(1, "one");
        Assert.assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(dictionary.isEmpty());
        dictionary.put(1, "one");
        Assert.assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testGet() {
        dictionary.put(1, "one");

        Assert.assertEquals("one", dictionary.get(1));
    }

    @Test
    public void testKeys() {
        dictionary.put(1, "one");
        dictionary.put(2, "two");

        Enumeration<Integer> keys = dictionary.keys();

        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            Assert.assertTrue(key == 1 || key == 2);
        }
    }

    @Test
    public void testElements() {
        dictionary.put(1, "one");
        dictionary.put(2, "two");

        Enumeration<String> elements = dictionary.elements();

        while (elements.hasMoreElements()) {
            String value = elements.nextElement();
            Assert.assertTrue("one".equals(value) || "two".equals(value));
        }
    }

    @Test
    public void testRemove() {
        Assert.assertNull(dictionary.remove(1));
        dictionary.put(1, "one");
        Assert.assertEquals("one", dictionary.remove(1));
    }

    @Test
    public void testSize() {
        dictionary.put(1, "one");
        Assert.assertEquals(1, dictionary.size());
    }
}
