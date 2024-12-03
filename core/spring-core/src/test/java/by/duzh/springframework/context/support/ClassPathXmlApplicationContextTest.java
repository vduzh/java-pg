package by.duzh.springframework.context.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassPathXmlApplicationContextTest {

    private static String configLocation;

    //new ClassPathXmlApplicationContext("classpath:**/context/" + configLocation)

    @BeforeAll
    static void setup() {
        configLocation = ClassPathXmlApplicationContextTest.class
                .getPackageName().replace('.', '/') + "/app-context-foo.xml";
    }

    @Test
    void create() throws Exception {
        try (var ctx = new ClassPathXmlApplicationContext(configLocation)) {
            List<String> beanDefinitionNames = Arrays.asList(ctx.getBeanDefinitionNames());
            assertTrue(beanDefinitionNames.contains("foo"));
        }
    }

    @Test
    void setConfigLocation() throws Exception {
        try (var ctx = new ClassPathXmlApplicationContext()) {
            ctx.setConfigLocation(configLocation);
            ctx.refresh();

            List<String> beanDefinitionNames = Arrays.asList(ctx.getBeanDefinitionNames());
            assertTrue(beanDefinitionNames.contains("foo"));
        }
    }
}