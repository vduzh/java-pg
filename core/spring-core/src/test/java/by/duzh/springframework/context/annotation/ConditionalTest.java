package by.duzh.springframework.context.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Look at https://habr.com/ru/post/487980/
public class ConditionalTest {

    static class ConditionOnAlwaysMatch implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class ConditionOnNeverMatch implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    @Conditional(value = ConditionOnAlwaysMatch.class)
    @Component("testComponent")
    static class TestComponent {}

    @Conditional(value = ConditionOnNeverMatch.class)
    @Component
    static class TestConfig {
        @Bean
        public String foo() {return "bar";}
    }

    @Component
    static class TestBeanConfig {

        @Conditional(value = ConditionOnAlwaysMatch.class)
        @Bean
        public String bar() {return "bar";}
    }

    @Test
    void test() {
        var ctx = new AnnotationConfigApplicationContext(TestComponent.class, TestConfig.class, TestBeanConfig.class);

        assertTrue(ctx.containsBean("testComponent"));
        assertFalse(ctx.containsBean("foo"));
        assertTrue(ctx.containsBean("bar"));
    }
}
