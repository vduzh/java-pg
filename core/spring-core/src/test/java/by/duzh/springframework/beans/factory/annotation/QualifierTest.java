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
        public String foo() {
            return "FooValue";
        }

        @Bean
        public String bar() {
            return "BarValue";
        }

        @Bean
        @Autowired
        public String fooAndBar(@Qualifier("foo") String fooValue, @Qualifier("bar") String barValue) {
            return fooValue + "And" + barValue;
        }

    }

    @Autowired
    @Qualifier("bar")
    String s;

    @Autowired
    @Qualifier("foo")
    String s2;

    @Autowired
    @Qualifier("fooAndBar")
    String s3;

    @Test
    void name() throws Exception {
        assertEquals("BarValue", s);
        assertEquals("FooValue", s2);
        assertEquals("FooValueAndBarValue", s3);
    }
}