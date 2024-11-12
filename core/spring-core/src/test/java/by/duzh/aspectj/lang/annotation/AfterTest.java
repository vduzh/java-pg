package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AfterTest {

    public static class FooService {
        public void foo() throws NumberFormatException {
            System.out.println("FooService: foo is throwing...");
            throw new NumberFormatException();
        }

        public void bar() {
            System.out.println("FooService: bar is working...");
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
    public static class AfterAspect {
        @After("execution(void bar())")
        public void afterAdvice() {
            System.out.println("AfterAspect: afterAdvice is working...");
        }

        @After("execution(void foo())")
        public void afterErrorAdvice() {
            System.out.println("AfterAspect: afterErrorAdvice is working...");
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AfterAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);

        fooService.bar();
        assertThrows(NumberFormatException.class, fooService::foo);
    }
}