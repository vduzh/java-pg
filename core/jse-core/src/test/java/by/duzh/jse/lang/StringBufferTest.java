package by.duzh.jse.lang;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class StringBufferTest {
    private StringBuffer sb;

    @BeforeEach
    public void init() {
        sb = new StringBuffer("test");
    }

    @Test
    public void testLengthCapacity() {
        Assertions.assertEquals(4, sb.length());
        Assertions.assertEquals(20, sb.capacity());
    }

    @Test
    public void testEnsureCapacity() {
        sb.ensureCapacity(1000);
        Assertions.assertEquals(4, sb.length());
        Assertions.assertEquals(1000, sb.capacity());
    }

    @Test
    public void testSetLength() {
        sb.setLength(6);
        Assertions.assertEquals(6, sb.length());
        // TODO:
        //System.out.println('-' + String.valueOf(sb) + '-');
        //Assert.assertEquals("test  ", sb.toString());
    }

    @Test
    public void testInsert() {
        sb.insert(1, 123);
        Assertions.assertEquals("t123est", sb.toString());
    }

    @Test
    public void testReverse() {
        Assertions.assertEquals("tset", sb.reverse().toString());
    }

    @Test
    public void testReplace() {
        sb = sb.replace(1, 3, "123456789");
        Assertions.assertEquals("t123456789t", sb.toString());
    }

    @Test
    public void testJDK11CompareTo() {
        Assertions.assertTrue(new StringBuffer("abc").compareTo(new StringBuffer("abc")) == 0);
        Assertions.assertTrue(new StringBuffer("ABC").compareTo(new StringBuffer("abc")) < 0);
        Assertions.assertTrue(new StringBuffer("abc").compareTo(new StringBuffer("ABC")) > 0);
    }
}
