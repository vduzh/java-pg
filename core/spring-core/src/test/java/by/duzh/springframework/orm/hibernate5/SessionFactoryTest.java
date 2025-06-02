package by.duzh.springframework.orm.hibernate5;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(SessionFactoryTest.AppConfig.class)
public class SessionFactoryTest {
    private static final Logger logger = Logger.getLogger(SessionFactoryTest.class.getName());

    @Configuration
    @Import(DataSourceTestConfig.class)
    static class AppConfig {
        @Autowired
        private DataSource dataSource;

        @Bean
        public LocalSessionFactoryBean sessionFactory1() {
            var sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);
            sessionFactoryBean.setPackagesToScan("by.duzh.springframework.orm.hibernate5.beans");
            return sessionFactoryBean; //Note: Factory Bean. Not Factory!!!
        }

        @Bean
        public JtaTransactionManager transactionManager() {
            return new JtaTransactionManager();
        }

        @Bean
        public LocalSessionFactoryBean sessionFactory11(JtaTransactionManager transactionManager) {
            var sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);
            sessionFactoryBean.setPackagesToScan("by.duzh.springframework.orm.hibernate5.beans");
            sessionFactoryBean.setJtaTransactionManager(transactionManager);
            return sessionFactoryBean;
        }

        @Bean
        public SessionFactory sessionFactory2() {
            var sessionFactory = new LocalSessionFactoryBuilder(dataSource)
                    .scanPackages("by.duzh.springframework.orm.hibernate5.beans")
                    .buildSessionFactory();

            return sessionFactory;
        }

        //@Bean
        public JndiObjectFactoryBean jndiSessionFactory() {
            JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
            factoryBean.setJndiName("java:comp/env/jdbc/myDataSource");
            return factoryBean;
        }

    }

    @Autowired
    private SessionFactory sessionFactory1;

    @Autowired
    private SessionFactory sessionFactory11;

    @Autowired
    private SessionFactory sessionFactory2;

    @Autowired
    private SessionFactory jndiSessionFactory;

    @Test
    void localSessionFactoryBean() {
        assertTrue(Arrays.stream(sessionFactory1.getStatistics().getEntityNames())
                .anyMatch(name -> name.contains("Foo")));
    }

    @Test
    void localSessionFactoryBeanWithJtaTransactionManager() {
        assertTrue(Arrays.stream(sessionFactory11.getStatistics().getEntityNames())
                .anyMatch(name -> name.contains("Foo")));
    }

    @Test
    void localSessionFactoryBuilder() {
        assertTrue(Arrays.stream(sessionFactory2.getStatistics().getEntityNames())
                .anyMatch(name -> name.contains("Foo")));
    }

    @Test
    public void jndiObjectFactoryBean() {
        assertNotNull(jndiSessionFactory);
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}