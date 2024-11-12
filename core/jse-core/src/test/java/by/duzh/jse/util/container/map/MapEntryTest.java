package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MapEntryTest {
    private Map.Entry<Integer, String> entry;

    @Before
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
        Assert.assertEquals(1, entry.getKey().intValue());
    }

    @Test
    public void testGetValue() {
        Assert.assertEquals("11", entry.getValue());
    }

    @Test
    public void testSetValue() {
        String oldValue = entry.setValue("foo");

        Assert.assertEquals("11", oldValue);
        Assert.assertEquals("foo", entry.getValue());
    }

}
