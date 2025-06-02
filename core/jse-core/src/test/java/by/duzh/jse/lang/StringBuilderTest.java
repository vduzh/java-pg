package by.duzh.jse.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//TODO: Implement
public class StringBuilderTest {

    @Test
    public void testJDK11CompareTo() {
        Assertions.assertTrue(new StringBuilder("abc").compareTo(new StringBuilder("abc")) == 0);
        Assertions.assertTrue(new StringBuilder("ABC").compareTo(new StringBuilder("abc")) < 0);
        Assertions.assertTrue(new StringBuilder("abc").compareTo(new StringBuilder("ABC")) > 0);
    }
}
