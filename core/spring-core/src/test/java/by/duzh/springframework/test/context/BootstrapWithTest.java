package by.duzh.springframework.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.support.DefaultTestContextBootstrapper;
import java.util.logging.Logger;

@BootstrapWith(DefaultTestContextBootstrapper.class)
public class BootstrapWithTest {
    private static final Logger logger = Logger.getLogger(BootstrapWithTest.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
