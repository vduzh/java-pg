package by.duzh.springframework.orm.jpa;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(EntityManagerFactoryTest.AppConfig.class)
public class EntityManagerFactoryTest {

    @Configuration
    @Import(DataSourceTestConfig.class)
    static class AppConfig {
        @Bean
        public LocalEntityManagerFactoryBean entityManagerFactory1() {
            var entityManagerFactoryBean = new LocalEntityManagerFactoryBean();
            entityManagerFactoryBean.setPersistenceUnitName("example-unit");
            return entityManagerFactoryBean; //Note: Factory Bean. Not Factory!!!
        }

        //@Bean
/*
        public Jndi jndiSessionFactory() {
            JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
            factoryBean.setJndiName("java:comp/env/jdbc/myDataSource");
            return factoryBean;
        }

 */

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            return new HibernateJpaVendorAdapter();
        }

        @Bean
        public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory3") EntityManagerFactory emf) {
            return new JpaTransactionManager(emf);
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory3(DataSource dataSource) {
            var factoryBean = new LocalContainerEntityManagerFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
            factoryBean.setPackagesToScan("by.duzh.springframework.orm.jpa.beans");
            return factoryBean;
        }
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory1;

    @Autowired
    private EntityManagerFactory entityManagerFactory3;

    @Test
    void localEntityManagerFactoryBean() {
        assertTrue(entityManagerFactory1.getMetamodel().getEntities().stream().anyMatch(e -> e.getName().equals("Foo")));
    }

    @Test
    void localContainerEntityManagerFactoryBean() {
        assertTrue(entityManagerFactory3.getMetamodel().getEntities().stream().anyMatch(e -> e.getName().equals("Foo")));
    }
}
