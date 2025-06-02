package by.duzh.jse.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SuperClass {
    public int a = 1;
    int b = 2;
    protected int c = 3;

    // not visible in a superclass
    private int d;

    // to be hidden
    public int h = 7;

    public SuperClass() {
        this.d = 4;
    }

    public SuperClass(int d) {
        this.d = d;
    }

    public int getD() {
        return d;
    }

    public int foo() {
        return 10;
    }
}

class SubClass extends SuperClass {
    public int e = 5;
    private int f;

    // hide the superclass h
    public int h = 8;

    public SubClass() {
        //super();
        this.f = 6;
    }

    public SubClass(int f) {
        //super();
        this.f = f;
    }

    public SubClass(int d, int f) {
        // user the super keyword
        super(d);
        this.f = f;
    }

    public int getF() {
        return f;
    }

    public int foo() {
        return super.foo() * 10;
    }
}

public class InheritanceTests {
    @Test
    public void testCreate() {
        // create a superclass
        SuperClass sup = new SuperClass();
        Assertions.assertEquals(4, sup.getD());
        sup = new SuperClass(10);
        Assertions.assertEquals(10, sup.getD());

        // create a subclass
        SubClass sub = new SubClass();
        Assertions.assertEquals(4, sub.getD());
        Assertions.assertEquals(6, sub.getF());

        sub = new SubClass(100);
        Assertions.assertEquals(4, sub.getD());
        Assertions.assertEquals(100, sub.getF());

        sub = new SubClass(1_000, 2_000);
        Assertions.assertEquals(1_000, sub.getD());
        Assertions.assertEquals(2_000, sub.getF());
    }

    @Test
    public void testAccessSuperClassMembers() {
        SubClass subClass = new SubClass();

        Assertions.assertEquals(1, subClass.a);
        Assertions.assertEquals(2, subClass.b);
        Assertions.assertEquals(3, subClass.c);

        // 'd' has private access in 'by.duzh.jse.oop.SuperClass'
        // Assertions.assertEquals(4, subClass.d);
    }

    @Test
    public void testSuperClassRef() {
        SuperClass obj1 = new SuperClass();
        SuperClass obj2 = new SubClass();
    }

    @Test
    public void testSuperForFields() {
        SuperClass sup = new SuperClass();
        Assertions.assertEquals(7, sup.h);

        SubClass sub = new SubClass();
        Assertions.assertEquals(8, sub.h);

        sub = new SubClass();
        Assertions.assertEquals(8, sub.h);
    }

    @Test
    public void testSuperForMethods() {
        SuperClass sup = new SuperClass();
        Assertions.assertEquals(10, sup.foo());

        SubClass sub = new SubClass();
        Assertions.assertEquals(100, sub.foo());
    }
}
