package by.duzh.springframework.transaction.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import java.util.logging.Logger;

public class TransactionalTest {
    private static final Logger logger = Logger.getLogger(TransactionalTest.class.getName());

    /*@Configuration
    @EnableTransactionManagement
    static class AppConfig {
    }*/

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
