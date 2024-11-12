package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

public class GenericClassHierarchiesTest {
    @Test
    public void testSubclassWithSimpleType() {
        class SubSimpleGeneric<T> extends SimpleGeneric<T> {
            SubSimpleGeneric(T value) {
                super(value);
            }
        }

        SubSimpleGeneric<String> obj = new SubSimpleGeneric<String>("test");
        Assert.assertEquals("test", obj.getValue());
    }

    @Test
    public void testSubclassWithSubclassOfTAndItsOwnGenericType() {
        class TwoTypesGeneric<V extends Number, T> extends SimpleGeneric<T> {
            private final V data;

            TwoTypesGeneric(V data, T value) {
                super(value);
                this.data = data;
            }

            public V getData() {
                return data;
            }
        }

        TwoTypesGeneric<Integer, String> obj = new TwoTypesGeneric<Integer, String>(123, "test");
        Assert.assertEquals(123, obj.getData().intValue());
        Assert.assertEquals("test", obj.getValue());
    }

    @Test
    public void testNonGenericSuperClass() {
        class NonGen {
        }

        class NonGenericSuperClass<T> extends NonGen {

            private final T data;

            NonGenericSuperClass(T data) {
                this.data = data;
            }

            public T getData() {
                return data;
            }
        }

        NonGenericSuperClass<String> obj = new NonGenericSuperClass<String>("test");
        Assert.assertEquals("test", obj.getData());
    }

    @Test
    public void testInstanceOf() {
        class Gen<T> {
        }
        class SubGen<T> extends Gen<T> {
        }

        Gen<Integer> obj1 = new Gen<Integer>();
        SubGen<Integer> obj2 = new SubGen<Integer>();

        Assert.assertTrue(obj1 instanceof Gen<?>);
        Assert.assertFalse(obj1 instanceof SubGen<?>);

        Assert.assertTrue(obj2 instanceof Gen<?>);
        Assert.assertTrue(obj2 instanceof SubGen<?>);
    }

    @Test
    public void testCast() {
        class Gen<T> {
        }
        class SubGen<T> extends Gen<T> {
        }

        Gen<Integer> obj1 = new Gen<Integer>();
        SubGen<Integer> obj2 = new SubGen<Integer>();

        // OK!
        Gen<Integer> res = (Gen<Integer>) obj2;
        // Not Ok!
        //var res2 = (Gen<Long>) obj2;
    }

    @Test
    public void testOverride() {
        class Gen<T> {
            String foo(T value) {
                return value.toString().toUpperCase();
            }
        }
        class SubGen<T> extends Gen<T> {
            @Override
            String foo(T value) {
                return super.foo(value) + " and " + value.toString().toLowerCase();
            }
        }

        SubGen<Integer> obj = new SubGen<Integer>();
        Assert.assertEquals("1 and 1", obj.foo(1));
    }

    @Test
    public void testShort() {
        SimpleGeneric<String> obj = new SimpleGeneric<>("test");
    }

    @Test
    public void testVar() {
        var obj = new SimpleGeneric<String>("test");
    }
}