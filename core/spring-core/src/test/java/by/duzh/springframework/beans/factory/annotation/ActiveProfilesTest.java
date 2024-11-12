package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringJUnitConfig(ActiveProfilesTest.TestConfig.class)
@SpringJUnitConfig // uses the inner class
@ActiveProfiles("dev")
public class ActiveProfilesTest {

    @Configuration
    static class TestConfig {
        @Bean(name = "test")
        @Profile("prod")
        public String foo() {
            return "foo";
        }

        @Bean(name = "test")
        @Profile("dev")
        public String bar() {
            return "bar";
        }
    }

    @Autowired
    ApplicationContext ctx;

    @Test
    public void test() throws Exception {
        assertEquals("bar", ctx.getBean("test", String.class));
    }
}
