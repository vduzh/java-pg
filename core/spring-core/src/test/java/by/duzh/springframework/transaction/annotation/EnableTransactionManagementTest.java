package by.duzh.springframework.transaction.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.logging.Logger;

public class EnableTransactionManagementTest {
    private static final Logger logger = Logger.getLogger(EnableTransactionManagementTest.class.getName());

    @Configuration
    @EnableTransactionManagement
    static class AppConfig {
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
