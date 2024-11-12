package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AfterThrowingTest {

    public static class FooService {
        public void foo() throws NumberFormatException {
            throw new NumberFormatException();
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
    public static class AfterThrowingAspect {
        @AfterThrowing("execution(void foo())")
        public void afterThrowingAdvice() {
            System.out.println("AfterThrowingAspect: afterThrowingAdvice is working...");
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AfterThrowingAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        assertThrows(NumberFormatException.class, fooService::foo);
    }

    @Aspect
    public static class AspectWithThrowing {
        @AfterThrowing(pointcut = "execution(void foo())",  throwing = "e")
        public void afterThrowingAdvice(JoinPoint joinPoint, Throwable e) {
            System.out.println("AspectWithThrowing: afterThrowingAdvice has catched " + e);
        }
    }

    @Test
    void testThrowing() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AspectWithThrowing.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        assertThrows(NumberFormatException.class, fooService::foo);
    }
}