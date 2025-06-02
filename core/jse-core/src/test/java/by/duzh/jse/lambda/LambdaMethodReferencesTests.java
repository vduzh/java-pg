package by.duzh.jse.lambda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


class CustomStringUtils {
    public static String lowerCase(String s) {
        return s.toLowerCase();
    }

    public String upperCase(String s) {
        return s.toUpperCase();
    }
}

interface StringFunc {
    String func(String s);
}

class LambdaAsArgumentDemo {
    public static String stringOperation(StringFunc sf, String s) {
        return sf.func(s);
    }
}

// TODO: Have a look at again!!!
public class LambdaMethodReferencesTests {

    @Test
    public void testReferenceToStaticMethod() throws Exception {
        String result = LambdaAsArgumentDemo.stringOperation(CustomStringUtils::lowerCase, "Test");
        Assertions.assertEquals("test", result);
    }

    @Test
    public void testReferenceToInstanceMethod() throws Exception {
        CustomStringUtils utils = new CustomStringUtils();

        String result = LambdaAsArgumentDemo.stringOperation(utils::upperCase, "test");
        Assertions.assertEquals("TEST", result);
    }

    @Test
    public void testReferencesToGenericMethod() {
        interface CustomFunctional<T> {
            int func(T[] values, T value);
        }

        class CustomArrayOps {
            public static <T> int countMatching(T[] values, T value) {
                int res = 0;
                for (T obj : values) {
                    if (obj == value) {
                        res++;
                    }
                }
                return res;
            }
        }

        CustomFunctional<String> objStr = CustomArrayOps::countMatching;
        int res = objStr.func(new String[]{"1", "2", "1", "3", "3"}, "1");
        Assertions.assertEquals(2, res);

        CustomFunctional<Integer> objInt = CustomArrayOps::countMatching;
        res = objInt.func(new Integer[]{1, 2, 1, 3, 3, 4, 5, 3, 7, 8, 3}, 3);
        Assertions.assertEquals(4, res);

    }

    @Test
    public void testReferenceToConstructor() throws Exception {
        interface CustomObjectCreator<T> {
            T create();
        }

        class Demo {
        }

        CustomObjectCreator<Demo> creator = Demo::new;
    }
}
