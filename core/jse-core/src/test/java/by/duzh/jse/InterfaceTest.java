package by.duzh.jse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

/**
 * Test interface
 */
// public by default
interface BasicInterface {

    // public by default
    int handle(int i);

    // final variable
    boolean YES = true;

    // default method -> supposed to be overridden
    default String getDefaultValue() {
        return "foo";
    }

    static void staticMethod() {
    }

    private static void privateStatic() {
    }

    static void callPrivateStatic() {
        privateStatic();
    }

    // private method -> can not be overridden
    private String privateMethod(String s) {
        return s.toUpperCase();
    }

    default String callPrivateMethod(String s) {
        return privateMethod(s);
    }
}

interface ExtendedInterface extends BasicInterface {
    int process(int i);
}

// might be either interface or class
class InterfaceContainer {
    public interface PublicInterface {
        int foo();
    }

    private interface PrivateInterface {
        int foo();
    }
}

/**
 * The BasicInterface implementation
 */
class BasicInterfaceImpl implements BasicInterface {
    // must be public
    @Override
    public int handle(int i) {
        return i * 10;
    }
}

/**
 * The BasicInterface implementation.
 * <p>
 * Must be abstract as it doesn't implement all the methods.
 */
abstract class BasicInterfacePartialImpl implements BasicInterface {
}

public class InterfaceTest {
    private static final Logger logger = Logger.getLogger(InterfaceTest.class.getName());

    @Test
    public void testImplementation() throws Exception {
        BasicInterfaceImpl obj = new BasicInterfaceImpl();
        Assertions.assertEquals(50, obj.handle(5));
    }

    @Test
    public void testReference() throws Exception {
        BasicInterface obj = new BasicInterfaceImpl();
        Assertions.assertTrue(obj instanceof BasicInterfaceImpl);
        Assertions.assertEquals(50, obj.handle(5));
    }

    @Test
    public void testVariables() throws Exception {
        Assertions.assertTrue(BasicInterface.YES);
        Assertions.assertTrue(BasicInterfaceImpl.YES);

        BasicInterfaceImpl obj = new BasicInterfaceImpl();
        Assertions.assertTrue(obj.YES);
    }

    @Test
    public void testCrateObjFromInterface() throws Exception {
        BasicInterface obj = new BasicInterface() {
            @Override
            public int handle(int i) {
                return 5;
            }
        };

        Assertions.assertEquals(5, obj.handle(0));
    }

    @Test
    public void testExtends() {
        class Foo implements ExtendedInterface {
            @Override
            public int handle(int i) {
                return 1;
            }


            @Override
            public int process(int i) {
                return 2;
            }
        }

        Foo foo = new Foo();
        Assertions.assertEquals(1, foo.handle(0));
        Assertions.assertEquals(2, foo.process(0));
    }

    @Test
    public void testInnerInterface() throws Exception {
        class Foo implements InterfaceContainer.PublicInterface {
            @Override
            public int foo() {
                return 5;
            }
        }

        Foo obj = new Foo();
        Assertions.assertEquals(5, obj.foo());
    }

    @Test
    public void testDefaultMethod() throws Exception {
        var i = new BasicInterfaceImpl();
        Assertions.assertEquals("foo", i.getDefaultValue());
    }


    @Test
    public void testStaticMethod() throws Exception {
        BasicInterface.staticMethod();
    }

    @Test
    public void testPrivateStaticMethod() throws Exception {
        BasicInterface.callPrivateStatic();
    }

    @Test
    public void testPrivateMethod() throws Exception {
        var i = new BasicInterfaceImpl();
        Assertions.assertEquals("FOO", i.callPrivateMethod("foo"));
    }

    @Test
    public void testConstantsSample() {
        interface Constants {
            String YES = "yes";
            String NO = "no";
        }

        Assertions.assertEquals("yes", Constants.YES);
        Assertions.assertEquals("no", Constants.NO);
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
