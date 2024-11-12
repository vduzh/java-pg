package by.duzh.springframework.context;

import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DefaultTestConfiguration.class, initializers = ApplicationContextInitializerTest.CustomContextIntializer.class)
public class ApplicationContextInitializerTest {
    static class CustomContextIntializer implements ApplicationContextInitializer<GenericApplicationContext> {
        @Override
        public void initialize(GenericApplicationContext applicationContext) {
            applicationContext.registerBean("test", String.class, "123");
        }
    }

    @Test
    void name(@Autowired String test, @Autowired String foo) {
        assertEquals("123", test);
        assertEquals("foo", foo);
    }
}