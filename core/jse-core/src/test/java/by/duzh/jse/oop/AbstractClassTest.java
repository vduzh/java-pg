package by.duzh.jse.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract class AbstractSuperClass {
    abstract public int foo();

    public int bar() {
        return foo() * 100;
    }
}

class SubClassForAbstractClass extends AbstractSuperClass {
    public int foo() {
        return 5;
    }
}

abstract class AbstractSuperClassWithNoAbstractMethods {
}

class SubClassWithNoAbstractMethods extends AbstractSuperClassWithNoAbstractMethods {
    public int buzz() {
        return 77;
    }
}

public class AbstractClassTest {

    @Test
    public void testAbstractClass() {
        SubClassForAbstractClass sub = new SubClassForAbstractClass();
        Assertions.assertEquals(500, sub.bar());

        SubClassWithNoAbstractMethods sub2 = new SubClassWithNoAbstractMethods();
        Assertions.assertEquals(77, sub2.buzz());
    }
}
