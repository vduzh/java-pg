package by.duzh.jse.lambda;

import by.duzh.jse.lambda.foo.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class LambdaCoreTests {

    @Test
    public void testLambdaSingleLineExpressionWithNoParams() throws Exception {
        MyNumber myNumber = () -> 123.5;
        Assert.assertEquals(123.5, myNumber.getValue(), 0);
    }

    @Test
    public void testLambdaExtendedExpression() throws Exception {
        MyNumber myNumber = () -> Math.random() * 100;
    }

    @Test
    public void testLambdaExpressionWithParam() throws Exception {
        NumericTest isEven = (i) -> (i % 2) == 0;
        Assert.assertFalse( isEven.test(3));
    }

    @Test
    public void testLambdaExpressionWithTwoParams() throws Exception {
        NumericTest2 compare = (i, j) -> i < j;
        Assert.assertTrue(compare.test(-1, 2));
    }

    @Test
    public void testLambdaExpressionWithParamAndTypes() throws Exception {
      //NumericTest2 compare = (int i, j) -> i > j; - INCORRECT
        NumericTest2 compare = (int i, int j) -> i > j;
        Assert.assertFalse( compare.test(1, 2));
    }

    @Test
    public void testLambdaExpressionWithSingleParam() throws Exception {
        // No parentheses surrounding a param
        NumericTest numericTest = i -> (i % 2) == 0;
        Assert.assertTrue(numericTest.test(4));
    }

    @Test
    public void testOtherLambdaSingleLineExpressionWithParamReferencingSameFunctionalInterface() throws Exception {
        NumericTest isEven = i -> i < 0;
        Assert.assertTrue(isEven.test(-1));
    }

    @Test
    public void testLambdaSimpleBlockOfCode() throws Exception {
        NumericFunc func = i -> {
            return i * i;
        };
        Assert.assertEquals(100, func.calculate(10));
    }

    @Test
    public void testLambdaBigBlockOfCode() throws Exception {
        NumericFunc func = (n) -> {
            int result = 1;
            for (int i = 1; i <= n; i++) {
                result = result * i;
            }

            return result;
        };
        Assert.assertEquals(120, func.calculate(5));
    }

    @Test
    public void testGenericFunctionalInterfaceWithInteger() {
        GenericFunc<Integer> obj = n -> {
            int result = 1;

            for (int i = 1; i <= n; i++) {
                result = result * i;
            }

            return result;
        };
        Assert.assertTrue(120 == obj.foo(5));
    }

    @Test
    public void testGenericFunctionalInterfaceWithString() {
        GenericFunc<String> obj = str -> {
            String result = "";

            for (int i = str.length() - 1; i >= 0; i--) {
                result += str.charAt(i);
            }

            return result;
        };
        Assert.assertEquals("olleH", obj.foo("Hello"));
    }

    @Test
    public void testLambdaAsArgument() {
        String out = LambdaAsArgumentDemo.stringOperation(s -> s.toUpperCase(), "Hello");
        Assert.assertEquals("HELLO", out);
    }

    @Test
    public void testLambdaBlockAsArgument() {
        String out = LambdaAsArgumentDemo.stringOperation(s -> {
            String result = "";

            for (int i = s.length() - 1; i >= 0; i--) {
                result += s.charAt(i);
            }

            return result;
        },"Hello");
        Assert.assertEquals("olleH", out);
    }

    @Test(expected = IOException.class)
    public void testLambdaAndExceptions() throws Exception {
        MethodWithException obj = () -> {
            throw new IOException("foo");
        };

        obj.foo();
    }

    @Test
    public void testLocalVariableCapture() {
        int n = 10;
        MyNumber myNumber = () -> n;
        Assert.assertEquals(10.0, myNumber.getValue(), 0);
    }

    @Test
    public void testThisOfEnclosingObject() {
        GenericFunc<String> func = s -> this.getClass().getName();
        Assert.assertEquals(LambdaCoreTests.class.getName(), func.foo(""));
    }
}
