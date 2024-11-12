package by.duzh.springframework.transaction.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class EnableTransactionManagementTest {
    @Configuration
    @EnableTransactionManagement
    static class AppConfig {
    }

    @Test
    void name() {
        throw new RuntimeException();
    }
}
