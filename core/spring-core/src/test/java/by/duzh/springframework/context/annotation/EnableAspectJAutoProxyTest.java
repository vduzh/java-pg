package by.duzh.springframework.context.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

public class EnableAspectJAutoProxyTest {
    @Component("fooService")
    public static class FooService {
        public void foo() {
            System.out.println("FooService: foo is working...");
        }
    }

    @Aspect
    @Component("testAspect")
    public static class TestAspect {
        @Before("execution(* foo())")
        public void beforeAdvice() {
            System.out.println("TestAspect: beforeAdvice is working...");
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class AppConfig {
    }

    @Test
    void test() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FooService.class, TestAspect.class);
        var service = ctx.getBean("fooService", FooService.class);
        service.foo();
    }
}
