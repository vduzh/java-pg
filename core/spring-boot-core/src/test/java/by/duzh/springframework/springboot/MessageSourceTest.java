package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, MessageSourceTest.TestConfig.class})
public class MessageSourceTest {

    @EnableAutoConfiguration // Must be otherwise messages are not loaded
    static class TestConfig {
    }

    //@Value("#{custom.label.text}")
    String labelText;


    @Test
    void test(@Autowired MessageSource messageSource) {
        assertEquals("Hello!", messageSource.getMessage("custom.label.text", null, Locale.US));
        assertEquals("Hello!", labelText);
    }
}
