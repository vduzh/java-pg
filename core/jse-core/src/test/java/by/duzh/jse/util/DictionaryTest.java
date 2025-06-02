package by.duzh.jse.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

// Old class
// NOTE: it is an abstract class!!!
public class DictionaryTest {
    private Dictionary<Integer, String> dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Hashtable<>();
    }

    @Test
    public void testPut() {
        String s = dictionary.put(1, "one");
        Assertions.assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(dictionary.isEmpty());
        dictionary.put(1, "one");
        Assertions.assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testGet() {
        dictionary.put(1, "one");

        Assertions.assertEquals("one", dictionary.get(1));
    }

    @Test
    public void testKeys() {
        dictionary.put(1, "one");
        dictionary.put(2, "two");

        Enumeration<Integer> keys = dictionary.keys();

        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            Assertions.assertTrue(key == 1 || key == 2);
        }
    }

    @Test
    public void testElements() {
        dictionary.put(1, "one");
        dictionary.put(2, "two");

        Enumeration<String> elements = dictionary.elements();

        while (elements.hasMoreElements()) {
            String value = elements.nextElement();
            Assertions.assertTrue("one".equals(value) || "two".equals(value));
        }
    }

    @Test
    public void testRemove() {
        Assertions.assertNull(dictionary.remove(1));
        dictionary.put(1, "one");
        Assertions.assertEquals("one", dictionary.remove(1));
    }

    @Test
    public void testSize() {
        dictionary.put(1, "one");
        Assertions.assertEquals(1, dictionary.size());
    }
}
