package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfterReturningTest {

    public static class FooService {
        public void foo() {
            System.out.println("FooService: foo is working...");
        }

        public StringBuilder echo(String s) {
            return new StringBuilder(s);
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
        @AfterReturning("execution(void foo())")
        public void afterReturningAdvice() {
            System.out.println("TestAspect: beforeAdvice is working...");
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, TestAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        fooService.foo();
    }

    @Aspect
    public static class AspectWithReturning {
        @AfterReturning(pointcut = "execution(* echo(..))", returning = "sb")
        public void afterReturningAdvice(StringBuilder sb) {
            System.out.println("AspectWithReturning: afterReturningAdvice is working...");
            sb.replace(0, sb.length(), sb.toString().toUpperCase());
        }
    }

    @Test
    void returning() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AspectWithReturning.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        assertEquals("TEST", fooService.echo("test").toString());
    }
}