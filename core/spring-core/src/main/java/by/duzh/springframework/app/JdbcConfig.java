package by.duzh.springframework.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc")
@ComponentScan(basePackages = "by.duzh.springframework.app.dao.foo.jdbc")
public class JdbcConfig {
}