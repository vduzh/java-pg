package by.duzh.jse.util.logging;

import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

//TODO: write tests
public class LoggingTest {
    private static final Logger logger = Logger.getLogger(LoggingTest.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
