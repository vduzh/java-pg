package by.duzh.jse.oop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class StaticTestClass {
    // called first
    static int a = 10;
    // called afterwords
    static int b;

    // static initializer
    // called at the end of initialization process
    static {
      b = a * 10;
    }

    // static methods
    static int multiply(int c) {
        return a * b * c;
    }
}

public class StaticTests {
    @Test
    public void testMembers() {
        assertEquals(10, StaticTestClass.a);
        assertEquals(100, StaticTestClass.b);
        assertEquals(2_000, StaticTestClass.multiply(2));
    }
}
