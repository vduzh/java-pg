package by.duzh.jse.core;

import org.junit.Assert;
import org.junit.Test;

public class PatternMatchingTest {
    @Test
    public void testJDK14() throws Exception {
        Object obj = 123;

        if (obj instanceof Integer intValue) {
            Assert.assertEquals(123, intValue.intValue());
        }
    }
}
