package by.duzh.aspectj.lang.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointCutTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface SomeMarker {
    }

    @Component("fooService")
    public static class FooService {
        @SomeMarker
        public String getById(Integer id) {
            return "foo-" + id;
        }
    }

    @Component("barService")
    @SomeMarker
    public static class BarService {
        public String getById(Integer id) {
            return "bar-" + id;
        }
        public String getByName(String name) {return "bar-" + name;}
        public void add(Param p) {
            System.out.println("BarService: param added");
        }
    }

    @SomeMarker
    public static class Param {

    }

    @Configuration
    @EnableAspectJAutoProxy
    @ComponentScan(useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*PointCutTest.*Service")
    )
    public static class AppConfig {
    }

    /**
     * Test inline PointCuts
     */
    @Aspect
    public static class InlineAspect {
        @Before("execution(String getById(java.lang.Integer))")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void inlinePointCut() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, InlineAspect.class);
        FooService fooService = ctx.getBean("fooService", FooService.class);
        assertEquals("foo-1", fooService.getById(1));
    }

    /**
     * Test annotated PointCuts
     */
    @Pointcut("execution(String getByName(String))")
    public void getByNameMethodsWithStringParamReturningString() {
    }

    @Aspect
    public static class AnnotatedAspect {
        @Before("by.duzh.aspectj.lang.annotation.PointCutTest.getByNameMethodsWithStringParamReturningString()")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void annotatedPointCut() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AnnotatedAspect.class);
        BarService barService = ctx.getBean("barService", BarService.class);
        assertEquals("bar-b", barService.getByName("b"));
    }

    /**
     * Execution expression - matches methods
     *
     * @Before("execution(String get*(int))")
     * @Before("execution(* get*(int))")
     * @Before("execution(* *(int))")
     * @Before("execution(* *(*))") // any ONE param
     * @Before("execution(* *(..))") // any number of params
     * @Before("execution(* *(int, ..))") // the first param is int, the rest are any number of params
     * @Before("execution(public String getById(int))")
     * @Before("execution(String by.duzh.aspectj.lang.annotation.AspectTest.FooService.getById(int))")
     * @Before("execution(String by.duzh.aspectj..*Service.get*(..))")
     */
    @Aspect
    public static class ExecutionAspect {
        @Before("execution(String by.duzh..*Service.getById(java.lang.Integer))")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void testExecution() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, ExecutionAspect.class);
        assertEquals("foo-1", ctx.getBean(FooService.class).getById(1));
        assertEquals("bar-2", ctx.getBean(BarService.class).getById(2));
    }

    /**
     * within expression
     *
     * with([package].[class])
     * package  - .. and * wildcards could be used
     * class    - * wildcard could be used
     */

    @Aspect
    public static class WithinAspect {
        @Before("within(by.duzh.aspectj..Foo*)")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void testWithin() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, WithinAspect.class);
        assertEquals("foo-1", ctx.getBean(FooService.class).getById(1));
        assertEquals("bar-2", ctx.getBean(BarService.class).getById(2));
    }

    /**
     * args expression
     *
     * args(prm_type1, ..., prm_typeN)
     * prm_type - .. and * wildcards could be used
     */
    @Aspect
    public static class ArgsAspect {
        @Before("args(Integer)")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void testArgs() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, ArgsAspect.class);
        assertEquals("foo-1", ctx.getBean(FooService.class).getById(1));
        assertEquals("bar-2", ctx.getBean(BarService.class).getById(2));
    }

    /**
     * bean expression
     *
     * bean(name)
     */
    @Aspect
    public static class BeanAspect {
        @Before("bean(fooService)")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void testBean() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, BeanAspect.class);
        assertEquals("foo-1", ctx.getBean(FooService.class).getById(1));
        assertEquals("bar-2", ctx.getBean(BarService.class).getById(2));
    }

    /**
     * annotation expression
     *
     * @annotation(annotation_type)
     */
    @Aspect
    public static class AnnotationAspect {
        @Before("@annotation(by.duzh.aspectj.lang.annotation.PointCutTest.SomeMarker)") // full path!
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void annotationBean() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AnnotationAspect.class);
        assertEquals("foo-1", ctx.getBean(FooService.class).getById(1));
        assertEquals("bar-2", ctx.getBean(BarService.class).getById(2));
    }

    /**
     * @args expression
     *
     * @args(annotation_type)
     */
    @Aspect
    public static class AtArgsAspect {
        @Before("@args(by.duzh.aspectj.lang.annotation.PointCutTest.SomeMarker)")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void atArgsBean() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AtArgsAspect.class);
        ctx.getBean(BarService.class).add(new Param());
    }

    /**
     * @within expression
     *
     * @within(annotation_type)
     */
    @Aspect
    public static class AtWithinAspect {
        @Before("@within(by.duzh.aspectj.lang.annotation.PointCutTest.SomeMarker)")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void atWithinBean() {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AtWithinAspect.class);
        ctx.getBean(BarService.class).getById(1);
        ctx.getBean(BarService.class).getByName("2");
        ctx.getBean(FooService.class).getById(3);
    }

    /**
     * Test PointCuts combinations
     */
    @Pointcut("execution(String getById(java.lang.Integer))")
    private void getByIdMethodsWithIntegerParamReturningString() {
    }

    @Aspect
    public static class ComboAspect {
        @Before("getByIdMethodsWithIntegerParamReturningString() || getByNameMethodsWithStringParamReturningString()")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void combineOrPointCuts() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, ComboAspect.class);

        FooService fooService = ctx.getBean("fooService", FooService.class);
        BarService barService = ctx.getBean("barService", BarService.class);

        assertEquals("foo-1", fooService.getById(1));
        assertEquals("bar-2", barService.getById(2));
        assertEquals("bar-b", barService.getByName("b"));
    }

    @Aspect
    public static class ComboNotAspect {

        @Before("getByIdMethodsWithIntegerParamReturningString() && !getByNameMethodsWithStringParamReturningString()")
        public void beforeGetAdvice(JoinPoint jp) {
            print(getClass().getSimpleName(), "beforeGetAdvice", jp);
        }
    }

    @Test
    void combineNotPointCuts() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, ComboNotAspect.class);

        FooService fooService = ctx.getBean("fooService", FooService.class);
        BarService barService = ctx.getBean("barService", BarService.class);

        assertEquals("foo-123", fooService.getById(123));
        assertEquals("bar-456", barService.getById(456));
        assertEquals("bar-b", barService.getByName("b"));
    }

    /**
     * Test JoinPoint
     */
    @Aspect
    public static class AspectWithJoinPoint {

        @Before("getByIdMethodsWithIntegerParamReturningString() || getByNameMethodsWithStringParamReturningString()")
        public void beforeGetAdvice(JoinPoint joinPoint) {
            var signature = (MethodSignature) joinPoint.getSignature();
            var methodName = signature.getName();
            Object[] args = joinPoint.getArgs();

            System.out.println("AspectWithJoinPoint: beforeGet is working for " + methodName +
                    " with params " + Arrays.toString(args));
        }
    }

    @Test
    void joinPoint() throws Exception {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, AspectWithJoinPoint.class);

        FooService fooService = ctx.getBean("fooService", FooService.class);
        BarService barService = ctx.getBean("barService", BarService.class);

        assertEquals("foo-123", fooService.getById(123));
        assertEquals("bar-b", barService.getByName("b"));
    }

    private static void print(String aspectName, String adviceName, JoinPoint jp) {
        var signature = (MethodSignature) jp.getSignature();
        var methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();
        Object[] args = jp.getArgs();

        System.out.printf("%s: %s is working for '%s' method with params %s%n", aspectName, adviceName, methodName,
                Arrays.toString(args));
    }
}
