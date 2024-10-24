package by.duzh.jse.lambda;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(123.5, number.getValue(), 0);

        number = () -> 10 * 100 + 10;
        Assert.assertEquals(1_010, number.getValue(), 0);
    }


    @Test
    public void testLambdaExpressionWithOneParam() throws Exception {
        // functional interface with a param
        interface SomeTest {
            boolean test(int n);
        }

        SomeTest isEven = (i) -> (i % 2) == 0;
        Assert.assertFalse(isEven.test(3));
        Assert.assertTrue(isEven.test(4));

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
        Assert.assertTrue(compare.test(1, 2));
        Assert.assertFalse(compare.test(2, 1));

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
        Assert.assertEquals(100, number.getValue(), 0);

        // block with more code inside
        number = () -> {
            int result = 1;
            for (int i = 1; i <= 5; i++) {
                result = result * i;
            }
            return result;
        };
        Assert.assertEquals(120, number.getValue(), 0);

    }

    @Test
    public void testGenericFunctionalInterfaceWithInteger() {
        // Generic functional interface
        interface GenericFunc<T> {
            T foo(T t);
        }

        // with integer type
        GenericFunc<Integer> intObj = n -> n * n;
        Assert.assertEquals(25, intObj.foo(5).intValue());

        // with string type
        GenericFunc<String> strObj = s -> s + s;
        Assert.assertEquals("1212", strObj.foo("12"));
    }

    @Test
    public void testLambdaAsArgument() {
        class Demo {
            static double foo(CustomNumber number) {
                return number.getValue();
            }
        }

        double res = Demo.foo(() -> 10);
        Assert.assertEquals(10, res, 0);

        // use the block as arg
        res = Demo.foo(() -> {
            return 10 * 10;
        });
        Assert.assertEquals(100, res, 0);

    }


    @Test(expected = IOException.class)
    public void testLambdaAndExceptions() throws Exception {
        // A functional interface
        interface CustomFunctional {
            void foo() throws IOException;
        }

        // use lambda
        CustomFunctional obj = () -> {
            throw new IOException("foo");
        };
        // simulate exception
        obj.foo();
    }

    @Test
    public void testLocalVariableCapture() {
        int n = 10;
        CustomNumber number = () -> n;
        Assert.assertEquals(10.0, number.getValue(), 0);
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

        Assert.assertEquals("10.0", new Demo().say());
    }
}
