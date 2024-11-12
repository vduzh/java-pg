package by.duzh.springframework.transaction.jta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(JtaTransactionManagerTest.TransactionManagerConfig.class)
public class JtaTransactionManagerTest {
    @Configuration
    public static class TransactionManagerConfig {
        @Bean
        public JtaTransactionManager transactionManager() {
            // does not need to know about the DataSource (or any other specific resources) because
            // it uses the container’s global transaction management infrastructure.
            var txManager = new JtaTransactionManager();
            // TODO: use jndi lookup here
            return txManager;
        }
    }

    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    void name() {
        assertNotNull(transactionManager);
    }
}