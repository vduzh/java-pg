package by.duzh.springframework.orm.hibernate5;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceTestConfig.class)
public class HibernateTestConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("by.duzh.springframework.orm.hibernate5.beans")
                .buildSessionFactory();
    }
}