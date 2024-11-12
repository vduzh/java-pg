package by.duzh.springframework.context.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageSourceAccessorTest {
    private MessageSourceAccessor accessor;

    @BeforeAll
    void beforeAll() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("foo", Locale.US, "bar");
        messageSource.addMessage("foo", Locale.GERMANY, "bar_DE");

        accessor = new MessageSourceAccessor(messageSource);
    }

    @Test
    void test() throws Exception {
        assertEquals("bar", accessor.getMessage("foo"));
        assertEquals("bar_DE", accessor.getMessage("foo", Locale.GERMANY));
        assertEquals("default", accessor.getMessage("unknown", "default"));
        // etc.
    }
}

