package by.duzh.jse.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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

    @BeforeEach
    public void init() {
        map.put(TestEnum.TWO, "two");
        map.put(TestEnum.ONE, "one");
        map.put(TestEnum.THREE, "three");
    }

    @Test
    public void testOrder() {
        Assertions.assertEquals("one", map.get(TestEnum.ONE));
        Assertions.assertEquals("three", map.get(TestEnum.THREE));
    }
}
