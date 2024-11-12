package by.duzh.springframework.transaction.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalTest {
    /*@Configuration
    @EnableTransactionManagement
    static class AppConfig {
    }*/

    @Test
    void name() {
        //Transactional
        throw new RuntimeException();
    }
}
