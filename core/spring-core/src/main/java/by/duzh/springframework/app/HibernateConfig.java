package by.duzh.springframework.app;

import by.duzh.springframework.app.model.foo.Foo;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;

@Configuration
@Profile("hibernate")
@ComponentScan(basePackages = "by.duzh.springframework.app.dao.foo.hibernate")
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("by.duzh.springframework.app.model")
                //.addAnnotatedClasses(Foo.class)
                .buildSessionFactory();
    }
}