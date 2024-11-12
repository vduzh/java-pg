package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// TODO: Use just @SpringJUnitConfig and refactor the remaining code
@SpringJUnitConfig(ValueTest.AppConfig.class)
public class ValueTest {
    @Component
    static class TestBean {
        String a;
        String b1, b2;
        String c1, c2;
        String d;
        String e;

        // #1 Field annotation
        @Value("bar") // simple value
        String bar;

        @Value("${user}") // property reference
        public String user;

        @Value("#{str}") // SpEL String
        String foo;

        // #2 Constructor
        public TestBean(@Value("a") String a) {
            this.a = a;
        }

        // #3 Method
        @Value("b")
        public void setTheSameValue(String b1, String b2) {
            this.b1 = b1;
            this.b2 = b2;
        }

        @Value("c1")
        public void setTwoValues(String c1, @Value("c2") String c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Autowired
        public void setOneValue(@Value("d") String d) {
            this.d = d;
        }

        // no either @Autowired or @Value annotations
        public void notSetAnyValue(@Value("e") String e) {
            this.e = e;
        }
    }

    @Configuration
    @ComponentScan(useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestBean.class
            ))
    @PropertySource(value = "classpath:by/duzh/springframework/beans/factory/annotation/messages.properties")
    static class AppConfig {
        @Bean
        public String str() { return "str"; }
    }

    @Autowired
    TestBean testBean;

    @Test
    void name() throws Exception {
        assertEquals("bar", testBean.bar);
        assertEquals("admin", testBean.user);
        assertEquals("str", testBean.foo);

        assertEquals("a", testBean.a);

        assertEquals("b", testBean.b1);
        assertEquals("b", testBean.b2);

        assertEquals("c1", testBean.c1);
        assertEquals("c2", testBean.c2);

        assertEquals("d", testBean.d);

        assertNull(testBean.e);
    }
}
