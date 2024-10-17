package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

class GenericConstructorDemo {
    private final double value;

    public <T extends Number> GenericConstructorDemo(T value) {
        this.value = value.doubleValue();
    }

    public double getValue() {
        return value;
    }
}

public class GenericConstructorsTest {
    @Test
    public void testGenericConstructorWithInteger() {
        GenericConstructorDemo obj = new GenericConstructorDemo(123);
        Assert.assertEquals(123.0, obj.getValue(), 0);
    }
}
