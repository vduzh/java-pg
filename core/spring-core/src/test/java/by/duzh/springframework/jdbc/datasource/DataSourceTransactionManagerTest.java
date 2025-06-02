package by.duzh.springframework.jdbc.datasource;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.Logger;

@SpringJUnitConfig(DataSourceTransactionManagerTest.TransactionManagerConfig.class)
public class DataSourceTransactionManagerTest {
    private static final Logger logger = Logger.getLogger(DataSourceTransactionManagerTest.class.getName());

    @Configuration
    @Import(DataSourceTestConfig.class)
    public static class TransactionManagerConfig {
        @Bean
        public DataSourceTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}