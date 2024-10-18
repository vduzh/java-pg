package by.duzh.jse;

import org.junit.Test;

public class TypeConversionTests {
    @Test
    public void testIntegers() throws Exception {
        int i = 123124;
        long l = i;

        short s = 32767;
        byte b = (byte) s;
    }

    @Test
    public void testFractionalNumbers() throws Exception {
        float f = 3.14F;
        double d = f;

        f = (float) d;
    }
}
