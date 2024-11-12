package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.ui.context.Theme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AroundTest {

    public static class FooService {
        public String foo() throws NumberFormatException {
            System.out.println("FooService: foo is throwing...");
            throw new NumberFormatException();
        }

        public String bar() {
            System.out.println("FooService: bar is working...");
            return "bar";
        }

        public String buz() throws NumberFormatException {
            System.out.println("FooService: buz is throwing...");
            throw new NumberFormatException();
        }

        public String xxx() throws NumberFormatException {
            System.out.println("FooService: xxx is throwing...");
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
    public static class AroundAspect {
        @Around("execution(String bar())")
        public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("AroundAspect: afterAdvice is starting");
            long start = System.currentTimeMillis();

            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();

            System.out.println("AroundAspect: the target method '" + joinPoint.getSignature().getName() + "' took " +
                    (end -start) + " milliseconds." );
            System.out.println("AroundAspect: afterAdvice finished!" );
            return result;
        }

        @Around("execution(String foo())")
        public Object aroundErrorDoNothingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("AroundAspect: aroundErrorDoNothingAdvice is started...");
            Object result = joinPoint.proceed();
            System.out.println("AroundAspect: aroundErrorDoNothingAdvice finished!" );
            return result;
        }

        @Around("execution(String buz())")
        public Object aroundErrorRetthrowAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("AroundAspect: aroundErrorRetthrowAdvice is started...");
            try {
                Object result = joinPoint.proceed();
                System.out.println("AroundAspect: aroundErrorRetthrowAdvice finished!");
                return result;
            } catch (Throwable e) {
                throw new Exception(e);
            }
        }

        @Around("execution(String xxx())")
        public Object aroundErrorDefaultValueAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("AroundAspect: aroundErrorDefaultValueAdvice is started...");
            Object result = null;
            try {
                result = joinPoint.proceed();
            } catch (Throwable e) {
                result = "default";
            }
            System.out.println("AroundAspect: aroundErrorDefaultValueAdvice finished!");
            return result;
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AroundAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);

        assertEquals("bar", fooService.bar());
        assertThrows(NumberFormatException.class, fooService::foo);
        assertThrows(Exception.class, fooService::buz);
        assertEquals("default", fooService.xxx());
    }
}