package by.duzh.springframework.context.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.StaticMessageSource;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationObjectSupportTest {
    // Declare ApplicationObjectSupport class
    public static class CustomObject extends ApplicationObjectSupport {
        public String sayHello(String name) {
            return getMessageSourceAccessor().getMessage("greeting", new Object[] {name}, Locale.US);
        }
    }

    // Configure app context
    @Configuration
    public static class AppConfig {
        @Bean
        public MessageSource messageSource() {
            var messageSource = new StaticMessageSource();
            messageSource.addMessage("greeting", Locale.US, "Hello {0}!");
            return messageSource;
        }

        @Bean
        public CustomObject foo() {
            return new CustomObject();
        }
    }

    private ApplicationContext ctx;

    @BeforeAll
    void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void test() throws Exception {
        CustomObject object = ctx.getBean(CustomObject.class);
        assertEquals("Hello World!",  object.sayHello("World"));
    }
}