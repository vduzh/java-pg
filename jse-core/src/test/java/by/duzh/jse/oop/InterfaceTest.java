package by.duzh.jse.oop;

import org.junit.Assert;
import org.junit.Test;

interface BasicInterface {
    // default method -> supposed to be overridden
    default String getDefaultValue() {
        return "";
    }

    static void staticMethod() {
    }

    private static void privateStatic() {
    }

    static void callPrivateStatic() {
        privateStatic();
    }

    // private method -> ca not be overridden
    private String privateMethod(String s) {
        return s.toUpperCase();
    }

    default String usePrivateMethod(String s) {
        return privateMethod(s);
    }
}

public class InterfaceTest {
    private BasicInterface i;

    @Test
    public void testStaticMethod() throws Exception {
        BasicInterface.staticMethod();
    }

    @Test
    public void testPrivateStaticMethod() throws Exception {
        BasicInterface.callPrivateStatic();
    }

    @Test
    public void testDefaultMethod() throws Exception {
        i = new BasicInterface() {};
        Assert.assertEquals("", i.getDefaultValue());
    }

    @Test
    public void testPrivateMethod() throws Exception {
        i = new BasicInterface() {};
        Assert.assertEquals("FOO", i.usePrivateMethod("foo"));
    }
}
