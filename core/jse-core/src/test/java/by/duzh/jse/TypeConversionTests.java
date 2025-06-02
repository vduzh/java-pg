package by.duzh.jse;

import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

public class TypeConversionTests {
    private static final Logger logger = Logger.getLogger(TypeConversionTests.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

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
