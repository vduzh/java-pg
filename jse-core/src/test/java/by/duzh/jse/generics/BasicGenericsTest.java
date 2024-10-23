package by.duzh.jse.generics;

import org.junit.Assert;
import org.junit.Test;

public class BasicGenericsTest {

    @Test
    public void genericBase() {
        SimpleGeneric<Integer> intGen = new SimpleGeneric<Integer>(10);
        int intValue = intGen.getValue();
        Assert.assertEquals(10, intValue);
        Assert.assertEquals("java.lang.Integer", intGen.getValueClassName());

        SimpleGeneric<String> strGen = new SimpleGeneric<String>("test");
        Assert.assertEquals("test", strGen.getValue());
        Assert.assertEquals("java.lang.String", strGen.getValueClassName());
    }

    @Test
    public void genericIncompatibility() {
        SimpleGeneric<Integer> intGen = new SimpleGeneric<Integer>(10);
        SimpleGeneric<String> strGen = new SimpleGeneric<String>("test");

        // IMPOSSIBLE: refs to different types
        //intGen = strGen
    }

    @Test
    public void genericWithTwoTypes() {
        GenericWithTwoTypes<String, Integer> gen = new GenericWithTwoTypes<String, Integer>("test", 123);
        Assert.assertEquals("test", gen.getObj1());
        Assert.assertEquals(123, gen.getObj2().intValue());
    }

    @Test
    public void boundedGeneric() {
        // bounded to a class
        BoundedGeneric<Integer> intValue = new BoundedGeneric<Integer>(123);
        double d = intValue.getDouble();
        Assert.assertEquals(123.0, d, 0);

        BoundedGeneric<Long> longValue = new BoundedGeneric<Long>(200L);
        d = longValue.getDouble();
        Assert.assertEquals(200.0, d, 0);

        // bounded to an interface
        class CompositeOne implements SomeInterface {
        }
        BoundedToInterfaceGeneric<CompositeOne> obj1 = new BoundedToInterfaceGeneric<CompositeOne>(new CompositeOne());
        Assert.assertEquals("foo", obj1.say());

        // bounded to multiple interfaces
        class CompositeTwo implements SomeInterface, SecondInterface {
        }
        BoundedToMultipleInterfacesGeneric<CompositeTwo> obj2 =
                new BoundedToMultipleInterfacesGeneric<CompositeTwo>(new CompositeTwo());
        Assert.assertEquals("foo and bar", obj2.say());

        // bounded to a class and multiple interfaces
        class CompositeThree extends Container implements SomeInterface, SecondInterface {
        }
        BoundedToClassAndInterfacesGeneric<CompositeThree> obj3 =
                new BoundedToClassAndInterfacesGeneric<CompositeThree>(new CompositeThree());
        Assert.assertEquals("100 and foo and bar", obj3.say());
    }
}
