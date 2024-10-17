package by.duzh.jse.util.container.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

// TODO: Look into the details of the implementation
// TODO: write tests
public class HashMapTest {
    private HashMap<Integer, String> map;

    @Before
    public void init() {
        map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
    }

    @Test
    public void test() {
    }
}
