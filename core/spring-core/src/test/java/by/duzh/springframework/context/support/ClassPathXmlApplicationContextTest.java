package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassPathXmlApplicationContextTest {

    //new ClassPathXmlApplicationContext("classpath:**/context/" + configLocation)

    @Test
    void create() throws Exception {
        var configLocation = getClass().getPackageName().replace('.', '/') + "/app-context-foo.xml";

        try (var ctx = new ClassPathXmlApplicationContext(configLocation)) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }

        try (var ctx = new ClassPathXmlApplicationContext()) {
            ctx.setConfigLocation(configLocation);
            ctx.refresh();

            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }
}