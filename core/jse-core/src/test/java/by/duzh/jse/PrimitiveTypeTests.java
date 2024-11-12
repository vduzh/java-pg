package by.duzh.jse;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveTypeTests {
    @Test
    public void testIntegers() throws Exception {
        // from -128 to 127
        byte b = 1;
        // from -32768 to 32767
        short s = 2;
        // 21....
        int i = 3;
        // ...
        long l = 4;

        int intValue = 10 * 20;

        long longValue = 10 * 20;
        longValue = longValue * 20L;
        longValue = intValue * longValue;
    }

    @Test
    public void testFractionalNumbers() throws Exception {
        // 4 bytes
        float f = 3.14F;
        System.out.println("float: " + f);

        // 8 bytes
        double d = 5.12;
        System.out.println("double: " + d);
    }

    @Test
    public void testChar() throws Exception {
        char c = 'a';
        System.out.println("char: " + c);
    }

    @Test
    public void testBoolean() throws Exception {
        boolean t = true;
        Assert.assertTrue(t);

        boolean f = false;
        Assert.assertFalse(f);
    }
}
