package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
public class QualifierTest {
    @Configuration
    static class AppConfig {
        @Bean
        public String foo() { return "foo"; }

        @Bean
        public String bar() { return "bar"; }
    }

    @Autowired
    @Qualifier("bar")
    String s;

    @Test
    void name() throws Exception {
        assertEquals("bar", s);
    }
}