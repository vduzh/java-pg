package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

public class BeforeTest {

    public static class FooService {
        public void foo() {
            System.out.println("FooService: foo is working...");
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class AppConfig {
        @Bean
        public FooService fooService() {
            return new FooService();
        }
    }

    @Aspect
    public static class TestAspect {
        @Before("execution(void foo())")
        public void beforeAdvice() {
            System.out.println("TestAspect: beforeAdvice is working...");
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, TestAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        fooService.foo();
    }
}