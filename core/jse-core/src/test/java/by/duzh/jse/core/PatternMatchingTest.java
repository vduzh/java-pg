package by.duzh.jse.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PatternMatchingTest {
    @Test
    public void testJDK14() throws Exception {
        Object obj = 123;

        if (obj instanceof Integer intValue) {
            Assertions.assertEquals(123, intValue.intValue());
        }
    }
}
