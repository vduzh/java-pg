package by.duzh.jse.lambda;

import by.duzh.jse.lambda.foo.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

// TODO: Have a look at again!!!
public class LambdaMethodReferencesTests {

    @Test
    public void testReferenceToStaticMethod() throws Exception {
        String result = LambdaAsArgumentDemo.stringOperation(MyStringOps::reverse, "123");
        Assert.assertEquals("321", result);
    }

    @Test
    public void testReferenceToStaticMethod2() throws Exception {
        String result = LambdaAsArgumentDemo.stringOperationWithoutValue(MyStringOps::reverse);
        Assert.assertEquals("ooF", result);
    }

    @Test
    public void testReferenceToInstanceMethod() throws Exception {
        MyStringOps stringOps = new MyStringOps();

        String result = LambdaAsArgumentDemo.stringOperation(stringOps::upperCase, "test");
        Assert.assertEquals("TEST", result);
    }

    @Test
    public void testReferenceToAnyInstanceMethod() throws Exception {
        Boolean result = LambdaAsArgumentDemo.compareStringsToInt(MyString::compareToInteger,
                new String[]{"10", "1", "13"}, 1);
        Assert.assertTrue("TEST", result);
    }

    @Test
    public void testReferenceToAnyInstanceMethod2() throws Exception {
        File[] files = new File(".").listFiles(File::isHidden);
        System.out.println(files.length);
    }

    @Test
    public void testReferencesToGenericMethodWithString() {
        MyFunc<String> obj = MyArrayOps::<String>countMatching;
        int res = obj.func(new String[]{"1", "2", "1", "3", "3"}, "1");
        Assert.assertEquals(2, res);
    }

    @Test
    public void testReferencesToGenericMethodWithInteger() {
        MyFunc<Integer> obj = MyArrayOps::<Integer>countMatching;
        int res = obj.func(new Integer[]{1, 2, 1, 3, 3, 4, 5, 3, 7, 8, 3}, 3);
        Assert.assertEquals(4, res);
    }

    @Test
    public void testReferenceToConstructor() throws Exception {
        MyObjectCreator<String, MyString> creator = MyString::new;
        MyString obj = creator.create("test");
        Assert.assertEquals("test", obj.getValue());
    }
}
