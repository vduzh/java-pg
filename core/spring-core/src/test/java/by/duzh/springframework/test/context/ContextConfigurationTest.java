package by.duzh.springframework.test.context;

import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)  // use it to have app context
//@ContextConfiguration(classes = DefaultTestConfiguration.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ContextConfigurationTest {

    @Configuration
    static class TestConfig {
        @Bean
        public String foo() {
            return "foo";
        }
    }

    @Autowired
    String foo;

    @Test
    void test(ApplicationContext ctx) {
        assertNotNull(ctx);
        assertEquals("foo", foo);
    }
}
