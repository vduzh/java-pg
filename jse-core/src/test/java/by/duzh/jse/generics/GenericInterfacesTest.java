package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

interface GenericInterfaceDemo<T extends Number> {
    double foo(T value);
}

class GenericInterfaceDemoImpl<T extends Number> implements GenericInterfaceDemo<T> {
    @Override
    public double foo(T value) {
        return value.doubleValue();
    }
}

public class GenericInterfacesTest {

    @Test
    public void testGenericInterfaceDemoWithInteger() {
        GenericInterfaceDemo<Integer> obj = new GenericInterfaceDemoImpl<Integer>();
        Assert.assertEquals(123.0, obj.foo(123), 0);
    }

}
