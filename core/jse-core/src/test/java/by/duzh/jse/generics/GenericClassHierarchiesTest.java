package by.duzh.jse.generics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class GenericClassHierarchiesTest {
    @Test
    public void testSubclassWithSimpleType() {
        class SubSimpleGeneric<T> extends SimpleGeneric<T> {
            SubSimpleGeneric(T value) {
                super(value);
            }
        }

        SubSimpleGeneric<String> obj = new SubSimpleGeneric<String>("test");
        Assertions.assertEquals("test", obj.getValue());
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
        Assertions.assertEquals(123, obj.getData().intValue());
        Assertions.assertEquals("test", obj.getValue());
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
        Assertions.assertEquals("test", obj.getData());
    }

    @Test
    public void testInstanceOf() {
        class Gen<T> {
        }
        class SubGen<T> extends Gen<T> {
        }

        Gen<Integer> obj1 = new Gen<Integer>();
        SubGen<Integer> obj2 = new SubGen<Integer>();

        Assertions.assertTrue(obj1 instanceof Gen<?>);
        Assertions.assertFalse(obj1 instanceof SubGen<?>);

        Assertions.assertTrue(obj2 instanceof Gen<?>);
        Assertions.assertTrue(obj2 instanceof SubGen<?>);
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
        Assertions.assertEquals("1 and 1", obj.foo(1));
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