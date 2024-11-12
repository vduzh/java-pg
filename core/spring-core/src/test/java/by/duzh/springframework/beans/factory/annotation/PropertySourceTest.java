package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(PropertySourceTest.AppConfig.class)
public class PropertySourceTest {

    @PropertySource(value = "classpath:by/duzh/springframework/beans/factory/annotation/messages.properties")
    public static class AppConfig {
        @Autowired
        public Environment env;

        @Bean
        public String hello() {
            return "Hello " + env.getProperty("foo") + "!";
        }
    }

    @Autowired
    private String foo;

    @Value("${user}")
    private String user;

    @Autowired
    public Environment env;

    @Test
    void test() {
        assertEquals("Hello bar!", foo);
        assertEquals("bar", env.getProperty("foo"));
        assertEquals("admin", env.getProperty("user"));
    }
}
