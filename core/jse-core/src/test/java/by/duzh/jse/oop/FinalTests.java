package by.duzh.jse.oop;

import org.junit.jupiter.api.Test;

class FinalTestClass {
    // applied to the instance field
    private final int a = 1;

    // applied to the static field
    public static final int FILE_NEW = 101;

    // final for the param
    public void foo(final StringBuffer sb) {
        // impossible
        // sb = new StringBuffer()

        // final for the local variable
        final int b = 2;
        // impossible
        // b = 22
    }

    public FinalTestClass() {
        // impossible
        // this.a = 2;
    }
}

public class FinalTests {
    @Test
    public void testMembers() {
        FinalTestClass ft = new FinalTestClass();
    }
}
