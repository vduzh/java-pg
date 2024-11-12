package by.duzh.jse.lang;

import org.junit.Assert;
import org.junit.Test;

//TODO: Implement
public class StringBuilderTest {
    @Test
    public void test() {
        throw new RuntimeException();
    }


    @Test
    public void testJDK11CompareTo() {
        Assert.assertTrue(new StringBuilder("abc").compareTo(new StringBuilder("abc")) == 0);
        Assert.assertTrue(new StringBuilder("ABC").compareTo(new StringBuilder("abc")) < 0);
        Assert.assertTrue(new StringBuilder("abc").compareTo(new StringBuilder("ABC")) > 0);
    }
}
