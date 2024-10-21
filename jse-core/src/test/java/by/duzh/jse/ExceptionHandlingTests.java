package by.duzh.jse;

import org.junit.Test;

import java.util.Random;

public class ExceptionHandlingTests {
    @Test(expected = IllegalArgumentException.class)
    public void testTryCatch() throws Exception {
        int i = new Random().nextInt();

        try {
            if (i % 2 == 0) {
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException();
            }
            // handle subclass
        } catch (ArithmeticException e) {
            System.out.println("First:" + e);
            // handle superclass
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Test()
    public void testTryFinally() throws Exception {
        try {
            System.out.println(1);
        } finally {
            System.out.println(2);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrows() throws Exception {
        class ThatThrowsException {
            static void foo() throws IllegalArgumentException, NullPointerException {
                throw new IllegalArgumentException();
            }
        }

        ThatThrowsException.foo();
    }
}
