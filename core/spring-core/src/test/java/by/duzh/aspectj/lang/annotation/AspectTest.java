package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AspectTest {

    @Configuration
    @EnableAspectJAutoProxy
    @ComponentScan(useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*AspectTest.*FooService")
    )
    public static class AppConfig {}

    @Component("fooService")
    public static class FooService {
        public String getById(int id) {
            return "foo-" + id;
        }
    }

    /**
     * Basic functionality
     */
    @Aspect
    public static class TestAspect {
        @Before("execution(String getById(int))") // Configure PointCut
        // Write Advice
        public void beforeAdvice() {
            System.out.println("TestAspect: before is working...");
        }
    }

    @Test
    void test() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, TestAspect.class);
        var fooService = ctx.getBean("fooService", FooService.class);
        assertEquals("foo-123", fooService.getById(123));
    }

    /**
     * Ordered piece of advice
     */
    @Aspect
    @Order(1)
    public static class FirstAspect {
        @Before("execution(String getById(int))")
        public void beforeAdvice() {
            System.out.println("FirstAspect: before is working...");
        }
    }

    @Aspect
    @Order(2)
    public static class SecondAspect {
        @Before("execution(String getById(int))")
        public void beforeAdvice() {
            System.out.println("SecondAspect: before is working...");
        }
    }

    @Test
    void orderedAdvice() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FirstAspect.class, SecondAspect.class);
        var fooService = ctx.getBean("fooService", FooService.class);
        assertEquals("foo-2", fooService.getById(2));
    }
}
