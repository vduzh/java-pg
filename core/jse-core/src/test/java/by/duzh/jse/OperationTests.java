package by.duzh.jse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.logging.Logger;

public class OperationTests {
    private static final Logger logger = Logger.getLogger(OperationTests.class.getName());

    @Test
    public void testIntegerDivision() throws Exception {
        Assertions.assertEquals(1, 5 / 3);
        Assertions.assertEquals(2, 5 % 3);
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
