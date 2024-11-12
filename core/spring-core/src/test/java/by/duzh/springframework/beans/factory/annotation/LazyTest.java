package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringJUnitConfig({LazyTest.AppConfig.class, LazyTest.AppConfig2.class, LazyTest.AllLazyConfig.class})
@SpringJUnitConfig
public class LazyTest {
    public static HashSet<String> beans = new HashSet<>();

    @Component("testObj")
    static class TestObj {
        public TestObj() {
            beans.add("testObj");
        }
    }

    @Component("lazyTestObj")
    @Lazy
    static class LazyTestObj {
        public LazyTestObj() {
            beans.add("lazyTestObj");
        }
    }

    @Configuration
    @ComponentScan(
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestObj.class)
    )
    static class AppConfig {
        @Bean // eager
        public Integer age() {
            beans.add("age");
            return 18;

        }

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  // lazy
        public Integer proto() {
            beans.add("proto");
            return 21;
        }


        @Lazy(value = false)  // eager
        @Bean
        public String foo() {
            beans.add("foo");
            return "foo";
        }

        @Bean
        @DependsOn("eagerPrototype")
        public String eager() {
            beans.add("eager");
            return "eager";
        }

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  // lazy but eager depend on it -> eager
        public String eagerPrototype() {
            beans.add("eagerPrototype");
            return "eagerPrototype";
        }

    }

    @Component("lazyInitTestObj")
    static class LazyInitTestObj {
        public LazyInitTestObj() {
            beans.add("lazyInitTestObj");
        }
    }

    @ComponentScan(
            lazyInit = true,
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LazyInitTestObj.class)
    )
    static class AppConfig2 {
    }

    @ComponentScan(
            lazyInit = true,
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LazyInitTestObj.class)
    )

    @Configuration
    @Lazy
    static class AllLazyConfig {
        @Bean
        public String allLazy() {
            beans.add("allLazy");
            return "allLazy";
        }
    }

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    void test() {
        assertTrue(beans.contains("age")); // eager
        assertFalse(beans.contains("proto")); // lazy
        assertTrue(beans.contains("foo")); // eager
        assertFalse(beans.contains("bar")); // lazy
        assertTrue(beans.contains("testObj")); // eager
        assertFalse(beans.contains("lazyTestObj")); // lazy

        assertTrue(beans.contains("eager")); // eager
        assertTrue(beans.contains("eagerPrototype")); // eager

        assertFalse(beans.contains("lazyInitTestObj")); // lazy

        assertFalse(beans.contains("allLazy")); // lazy
    }
}