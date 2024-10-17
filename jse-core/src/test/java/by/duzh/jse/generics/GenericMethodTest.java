package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

class GenMethodDemo {
    static <T extends Number, V extends Number> double foo(T t, V[] arr) {
        double result = t.doubleValue();
        for (V value : arr) {
            result += value.doubleValue();
        }
        return result;
    }
}

public class GenericMethodTest {
    @Test
    public void testGenericMethodWithInteger() {
        double result;
        result = GenMethodDemo.<Integer, Integer>foo(10, new Integer[]{1, 2, 3});
        Assert.assertEquals(16.0, result, 0);
    }

    @Test
    public void testGenericMethodWithDouble() {
        double result = GenMethodDemo.<Double, Double>foo(10.0, new Double[]{1.0, 2.0, 3.0});
        Assert.assertEquals(16.0, result, 0);
    }

    @Test
    public void testGenericMethodWithIntegerAndDouble() {
        double result = GenMethodDemo.<Integer, Double>foo(10, new Double[]{1.0, 2.0, 3.0});
        Assert.assertEquals(16.0, result, 0);
    }
}
