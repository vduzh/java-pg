package by.duzh.jse.util.container.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MapEntryTest {
    private Map.Entry<Integer, String> entry;

    @BeforeEach
    public void init() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "11");
        entry = map.entrySet().iterator().next();
    }

    @Test
    public void testComparingByKey() {
        // TODO:
        //Map.Entry.comparingByKey()
    }

    @Test
    public void testComparingByValue() {
        // TODO:
        //Map.Entry.comparingByValue()
    }

    @Test
    public void testGetKey() {
        Assertions.assertEquals(1, entry.getKey().intValue());
    }

    @Test
    public void testGetValue() {
        Assertions.assertEquals("11", entry.getValue());
    }

    @Test
    public void testSetValue() {
        String oldValue = entry.setValue("foo");

        Assertions.assertEquals("11", oldValue);
        Assertions.assertEquals("foo", entry.getValue());
    }

}
