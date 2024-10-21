package by.duzh.jse;

import org.junit.Test;

public class VarTests {
    @Test
    public void testVar() throws Exception {
        // standard approach
        long a = 1;
        // with var keyword
        var b = 1L;
        // with an object
        var s = "Hello";
    }
}
