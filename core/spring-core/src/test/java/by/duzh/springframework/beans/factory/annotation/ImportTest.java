package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportTest {
    @Configuration
    public static class AppConfig1 {
        @Bean
        public String foo() {
            return "foo";
        }
    }

    @Configuration
    @Import(AppConfig1.class)
    public static class AppConfig2 {
        @Autowired
        String foo;

        @Bean
        public String bar() {
            return foo + " bar";
        }
    }

    @Test
    void test() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig2.class);
        assertEquals("foo", ctx.getBean("foo", String.class));
        assertEquals("foo bar", ctx.getBean("bar", String.class));
    }
}
