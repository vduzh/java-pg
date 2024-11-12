package by.duzh.jse.oop;

import org.junit.Test;

// impossible to extend
final class FinalSuperClass {
}

class SuperClassWithFinalMethod {
    // impossible to override
    final public int foo() {
        return 100;
    }
}

public class FinalClassTest {

    @Test
    public void test() {
    }
}
