package by.duzh.jse.generics;

import by.duzh.jse.generics.etc.Gen;
import by.duzh.jse.generics.etc.NonGenericSuperClass;
import by.duzh.jse.generics.etc.SubСlassGen;
import by.duzh.jse.generics.etc.TwoTypesGen;
import org.junit.Assert;
import org.junit.Test;

public class GenericClassHierarchiesTest {
    @Test
    public void testSubclassWithSimpleType() {
        SubСlassGen<String> obj = new SubСlassGen<String>("test");
        Assert.assertEquals("test", obj.getValue());
    }

    @Test
    public void testSubclassWithSubclassOfTAndItsOwnGenericType() {
        TwoTypesGen<Integer, String> obj = new TwoTypesGen<Integer, String>(123, "test");
        Assert.assertEquals(123, obj.getValue().intValue());
        Assert.assertEquals("test", obj.getData());
    }

    @Test
    public void testNonGenericSuperClass() {
        NonGenericSuperClass<String> obj = new NonGenericSuperClass<String>("test", 123);
        Assert.assertEquals(123, obj.getValue());
        Assert.assertEquals("test", obj.getData());
    }

    @Test
    public void testInstanceOf() {
        Gen<Integer> obj = new Gen<Integer>(123);
        SubСlassGen<Integer> obj2 = new SubСlassGen<Integer>(345);

        Assert.assertTrue(obj instanceof Gen<?>);
        Assert.assertFalse(obj instanceof SubСlassGen<?>);

        Assert.assertTrue(obj2 instanceof Gen<?>);
        Assert.assertTrue(obj2 instanceof SubСlassGen<?>);
    }
}