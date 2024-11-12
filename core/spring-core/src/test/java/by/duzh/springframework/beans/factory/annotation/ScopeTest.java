package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScopeTest {
    @Configuration
    public static class AppConfig {
        @Bean
        @Scope("prototype")
        public String prototypeBeanSample() {
            return "prototype";
        }

        @Bean
        public  String singletonBeanSample() {
            return "singleton";
        }
    }

    private BeanFactory beanFactory;

    @BeforeAll
    public void setUp() {
        beanFactory = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void isPrototype() {
        assertTrue(beanFactory.isPrototype("prototypeBeanSample"));
    }

    @Test
    void isSingleton() {
        assertTrue(beanFactory.isSingleton("singletonBeanSample"));
    }
}
