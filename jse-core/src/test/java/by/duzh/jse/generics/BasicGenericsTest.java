package by.duzh.jse.generics;

import by.duzh.jse.generics.etc.Gen;
import by.duzh.jse.generics.etc.I;
import by.duzh.jse.generics.etc.NonGen;
import org.junit.Assert;
import org.junit.Test;

class GenericWithTwoTypes<T, V> {
    private final T obj1;
    private final V obj2;

    public GenericWithTwoTypes(T obj1, V obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public T getObj1() {
        return obj1;
    }

    public V getObj2() {
        return obj2;
    }

    public String getObj1Type() {
        return obj1.getClass().getName();
    }

    public String getObj2Type() {
        return obj2.getClass().getName();
    }
}

class BoundedGeneric<T extends Number> {
    private final T value;

    public BoundedGeneric(T value) {
        this.value = value;
    }

    public double getDouble() {
        return value.doubleValue();
    }
}

class CompositeGeneric<T extends NonGen & I> {
    private final T obj;

    public CompositeGeneric(T value) {
        this.obj = value;
    }

    public String getValue() {
        return obj.foo() + " " + obj.bar();
    }
}

public class BasicGenericsTest {
    @Test
    public void testSimpleGenericWithString() {
        Gen<String> obj = new Gen<String>("test");
        Assert.assertEquals("test", obj.getValue());
        Assert.assertEquals("java.lang.String", obj.getValueClassName());
    }

    @Test
    public void testSimpleGenericWithInteger() {
        Gen<Integer> obj = new Gen<Integer>(123);
        Assert.assertEquals(123, obj.getValue().intValue());
        Assert.assertEquals("java.lang.Integer", obj.getValueClassName());
    }

    @Test
    public void testGenericWithTwoTypes() {
        GenericWithTwoTypes<String, Integer> gen = new GenericWithTwoTypes<String, Integer>("test", 123);
        Assert.assertEquals("java.lang.String", gen.getObj1Type());
        Assert.assertEquals("java.lang.Integer", gen.getObj2Type());
        Assert.assertEquals("test", gen.getObj1());
        Assert.assertEquals(123, gen.getObj2().intValue());
    }

    @Test
    public void testBoundedGenericWithInteger() {
        BoundedGeneric<Integer> value = new BoundedGeneric<Integer>(123);
        double d = value.getDouble();
        Assert.assertEquals(123.0, d, 0);
    }

    @Test
    public void testBoundedGenericWithDouble() {
        BoundedGeneric<Double> value = new BoundedGeneric<Double>(123.45);
        double d = value.getDouble();
        Assert.assertEquals(123.45, d, 0);
    }

    @Test
    public void testCompositeGeneric() {
        class TestCompositeObject extends NonGen implements I {
        }

        CompositeGeneric<TestCompositeObject> value = new CompositeGeneric<TestCompositeObject>(new TestCompositeObject());
        String s = value.getValue();
        Assert.assertEquals("foo bar", s);
    }
}
