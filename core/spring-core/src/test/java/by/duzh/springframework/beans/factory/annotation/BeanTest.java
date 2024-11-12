package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

//@SpringJUnitConfig(BeanTest.AppConfig.class)
@SpringJUnitConfig
public class BeanTest {
    @Autowired
    private ApplicationContext ctx;

    static class SomeSource {
        private String data;

        public SomeSource(String data) {
            this.data = data;
        }

        public void init() {
            if (data == null) {
                data = "default";
            }
        }

        public void close() {
            // clean up
            this.data = null;
        }

        public String getData() {
            return data;
        }
    }

    @Configuration
    static class AppConfig {
        @Bean
        public Integer age() {
            return 18;
        }

        @Bean(autowireCandidate = false)
        public Integer salary() {
            return 3_000;
        }

        @Bean(name = {"f1", "f2"}) // bean available as 'f1' and 'f22', but not 'foo'
        public String foo() {
            return "foo";
        }

        @Bean({"b1", "b2"})
        public String bar() {
            return "bar";
        }

        @Bean(name = {"z1", "z2"})
        public String bazz() {
            return "bazz";
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public SomeSource someSource() {
            return new SomeSource("foo");
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public SomeSource someSource2() {
            return new SomeSource(null);
        }
    }

    @Test
    void test() throws Exception {
        ctx.getBean("age");

        ctx.getBean("f1");
        ctx.getBean("f2");
        assertThrows(Exception.class, () -> ctx.getBean("foo"));

        ctx.getBean("b1");
        ctx.getBean("b2");
        assertThrows(Exception.class, () -> ctx.getBean("bar"));

        ctx.getBean("z1");
        ctx.getBean("z2");
        assertThrows(Exception.class, () -> ctx.getBean("bazz"));

        assertEquals("foo", ctx.getBean("someSource", SomeSource.class).getData());
        assertEquals("default", ctx.getBean("someSource2", SomeSource.class).getData());

        // autowireCandidate
        assertEquals(18, ctx.getBean(Integer.class).intValue());
    }
}
