package by.duzh.springframework.test.context.web;

import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DefaultTestConfiguration.class)

// Ensures that a WebApplicationContext is loaded for the test, using the default value of "file:src/main/webapp"
// for the path to the root of the web application
@WebAppConfiguration("src/test/webapp") // "classpath:webapp"
public class WebAppConfigurationTest {

    @Test
    void test(WebApplicationContext ctx) {
        assertNotNull(ctx);
        //Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
