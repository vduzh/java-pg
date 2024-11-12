package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
public class PropertySourcesTest {

    @Configuration // Must be to have PropertySources working
    @PropertySources(
            @PropertySource(value = "classpath:by/duzh/springframework/beans/factory/annotation/messages.properties")
    )
    public static class AppConfig {
    }

    @Autowired
    public Environment env;

    @Test
    void test() {
        assertEquals("admin", env.getProperty("user"));
    }
}
