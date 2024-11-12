package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

// TODO: Look into the details of the implementation
// TODO: write tests
public class LinkedHashMapTest {
    private LinkedHashMap<Integer, String> map;

    @Before
    public void init() {
        map = new LinkedHashMap<>();
        map.put(2, "two");
        map.put(1, "one");
        map.put(3, "three");
    }

    @Test
    public void testOrder() {
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        Assert.assertEquals(2, iterator.next().getKey().intValue());
        Assert.assertEquals(1, iterator.next().getKey().intValue());
        Assert.assertEquals(3, iterator.next().getKey().intValue());
    }
}
