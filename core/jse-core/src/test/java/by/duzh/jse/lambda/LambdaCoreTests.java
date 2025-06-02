package by.duzh.jse.lambda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

// Functional interface
interface CustomNumber {
    double getValue();
}

public class LambdaCoreTests {

    @Test
    public void testLambdaSingleLineExpressionWithNoParams() throws Exception {
        // lambdas
        CustomNumber number = () -> 123.5;
        Assertions.assertEquals(123.5, number.getValue(), 0);

        number = () -> 10 * 100 + 10;
        Assertions.assertEquals(1_010, number.getValue(), 0);
    }


    @Test
    public void testLambdaExpressionWithOneParam() throws Exception {
        // functional interface with a param
        interface SomeTest {
            boolean test(int n);
        }

        SomeTest isEven = (i) -> (i % 2) == 0;
        Assertions.assertFalse(isEven.test(3));
        Assertions.assertTrue(isEven.test(4));

        // without parentheses surrounding a param
        isEven = i -> (i % 2) == 0;
    }

    @Test
    public void testLambdaExpressionWithTwoParams() throws Exception {
        // functional interface with params
        interface SomeTest {
            boolean test(int n1, int n2);
        }

        SomeTest compare = (i, j) -> i < j;
        Assertions.assertTrue(compare.test(1, 2));
        Assertions.assertFalse(compare.test(2, 1));

        // declare types
        compare = (int i, int j) -> i < j;
        // INCORRECT
        // compare = (int i, j) -> i < j;
    }

    @Test
    public void testLambdaSimpleBlockOfCode() throws Exception {
        CustomNumber number = () -> {
            return 100;
        };
        Assertions.assertEquals(100, number.getValue(), 0);

        // block with more code inside
        number = () -> {
            int result = 1;
            for (int i = 1; i <= 5; i++) {
                result = result * i;
            }
            return result;
        };
        Assertions.assertEquals(120, number.getValue(), 0);

    }

    @Test
    public void testGenericFunctionalInterfaceWithInteger() {
        // Generic functional interface
        interface GenericFunc<T> {
            T foo(T t);
        }

        // with integer type
        GenericFunc<Integer> intObj = n -> n * n;
        Assertions.assertEquals(25, intObj.foo(5).intValue());

        // with string type
        GenericFunc<String> strObj = s -> s + s;
        Assertions.assertEquals("1212", strObj.foo("12"));
    }

    @Test
    public void testLambdaAsArgument() {
        class Demo {
            static double foo(CustomNumber number) {
                return number.getValue();
            }
        }

        double res = Demo.foo(() -> 10);
        Assertions.assertEquals(10, res, 0);

        // use the block as arg
        res = Demo.foo(() -> {
            return 10 * 10;
        });
        Assertions.assertEquals(100, res, 0);

    }


    @Test
    public void testLambdaAndExceptions() {
        // A functional interface
        interface CustomFunctional {
            void foo() throws IOException;
        }

        // use lambda
        CustomFunctional obj = () -> {
            throw new IOException("foo");
        };
        // simulate exception
        Assertions.assertThrows(IOException.class, () -> obj.foo());
    }

    @Test
    public void testLocalVariableCapture() {
        int n = 10;
        CustomNumber number = () -> n;
        Assertions.assertEquals(10.0, number.getValue(), 0);
    }

    @Test
    public void testThisOfEnclosingObject() {
        class Demo {
            final int i = 10;

            String say() {
                CustomNumber number = () -> this.i;
                return String.valueOf(number.getValue());
            }
        }

        Assertions.assertEquals("10.0", new Demo().say());
    }
}
