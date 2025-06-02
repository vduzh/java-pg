package by.duzh.jse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.logging.Logger;

import java.util.Random;

@Disabled
public class ExceptionHandlingTests {
    private static final Logger logger = Logger.getLogger(ExceptionHandlingTests.class.getName());

    @Test
    public void testTryCatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            int i = new Random().nextInt();
            if ((i % 2) == 0) {
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException();
            }
        });
    }

    @Test
    public void testCatchMultipleExceptions() throws Exception {
        int i = new Random().nextInt();

        try {
            if ((i % 2) == 0) {
                throw new IllegalArgumentException();
            } else {
                throw new ArithmeticException();
            }
        } catch (ArithmeticException | IllegalArgumentException e) {
            System.out.println("First:" + e);
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

    @Test
    public void testThrows() throws Exception {
        throw new Exception();
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
