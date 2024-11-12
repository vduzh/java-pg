package by.duzh.springframework.context.support;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotationConfigApplicationContextTest {
    //Note: no Component annotation used
    static class Foo {
    }

    static class Bar {
        private Foo foo;

        public Bar(Foo foo) {
            this.foo = foo;
        }

        public Foo getFoo() {
            return foo;
        }
    }

    @Configuration
    @ComponentScan()
    static class AppConfig {
        @Bean
        public String str() {
            return "str";
        }

        @Bean
        public Foo foo() {
            return new Foo();
        }

        @Bean
        @Autowired
        public Bar bar(Foo f) {
            return new Bar(f);
        }
    }

    @Test
    void create() throws Exception {
        // Any class
        try (var ctx = new AnnotationConfigApplicationContext(Foo.class)) {
            assertTrue(Arrays.stream(ctx.getBeanDefinitionNames()).anyMatch(s -> s.contains("Foo")));
        }

        // Config class
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }

        // Register class and refresh
        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(Foo.class);
            ctx.refresh();
            ctx.getBean(Foo.class);
        }

        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(AppConfig.class);
            ctx.refresh();
            ctx.getBean("foo");
        }
    }
}