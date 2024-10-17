package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO: Look into the details of the implementation
// TODO: write tests
public class EnumMapTest {

    enum TestEnum {
        ONE,
        TWO,
        THREE
    }

    private EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);

    @Before
    public void init() {
        map.put(TestEnum.TWO, "two");
        map.put(TestEnum.ONE, "one");
        map.put(TestEnum.THREE, "three");
    }

    @Test
    public void testOrder() {
        Assert.assertEquals("one", map.get(TestEnum.ONE));
        Assert.assertEquals("three", map.get(TestEnum.THREE));
    }
}
