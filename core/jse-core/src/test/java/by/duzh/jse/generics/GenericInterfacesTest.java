package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

interface GenericInterfaceDemo<T extends Number> {
    double foo(T value);
}

// Type T declared on the GenericInterfaceDemoImpl type and passed on to the GenericInterfaceDemo
class GenericInterfaceDemoImpl<T extends Number> implements GenericInterfaceDemo<T> {
    @Override
    public double foo(T value) {
        return value.doubleValue();
    }
}

// NotGeneric
class NotGenericInterfaceDemoImpl implements GenericInterfaceDemo<Integer> {
    @Override
    public double foo(Integer value) {
        return value.doubleValue();
    }
}

public class GenericInterfacesTest {

    @Test
    public void testGenericInterfaceDemo() {
        GenericInterfaceDemo<Integer> obj = new GenericInterfaceDemoImpl<Integer>();
        Assert.assertEquals(123.0, obj.foo(123), 0);
    }

    @Test
    public void testNotGenericInterfaceDemoWithInteger() {
        NotGenericInterfaceDemoImpl obj = new NotGenericInterfaceDemoImpl();
        Assert.assertEquals(123.0, obj.foo(123), 0);
    }
}
