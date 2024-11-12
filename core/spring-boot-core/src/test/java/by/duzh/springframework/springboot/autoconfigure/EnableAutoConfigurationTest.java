package by.duzh.springframework.springboot.autoconfigure;

import by.duzh.springframework.springboot.beans.Bar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnableAutoConfigurationTest {

    @Configuration
    @EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
    @ComponentScan(basePackages = "by.duzh.springframework.springboot.beans")
    static class PrimaryConfig {
    }

    @Test
    void autoConfiguredBeansInSpringBoot() {
        var ctx = new SpringApplicationBuilder()
                .sources(PrimaryConfig.class)
                .web(WebApplicationType.NONE)
                .run();

        assertNotNull(ctx.getBean(DataSource.class));

        SpringApplication.exit(ctx);
    }

    @Test
    void autoConfiguredBeansInPureSpring() {
        // NOTE! We can use auto-configuration with Spring Context
        var ctx = new AnnotationConfigApplicationContext(PrimaryConfig.class);
        assertNotNull(ctx.getBean(DataSource.class));
        assertNotNull(ctx.getBean(Bar.class));
        ctx.close();
    }
}
