package by.duzh.springframework.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageSourceTest {
    // https://www.programcreek.com/java-api-examples/?api=org.springframework.context.support.StaticMessageSource
    private MessageSource messageSource;

    @BeforeAll
    public void setUp() throws Exception {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("foo", Locale.US, "foo_US");
        messageSource.addMessage("foo", Locale.GERMANY, "foo_DE");

        messageSource.addMessage("bar", Locale.US, "Hello {0}!");

        this.messageSource = messageSource;
    }

    @Test
    void getMessage() throws Exception {
        // test 2 locales
        assertEquals("foo_US", messageSource.getMessage("foo", null, Locale.US));
        assertEquals("foo_DE", messageSource.getMessage("foo", null, Locale.GERMANY));

        // test params
        assertEquals("Hello World!", messageSource.getMessage("bar", new Object[] {"World"}, Locale.US));

        // test the default value
        assertEquals("default_US", messageSource.getMessage("unknown", null, "default_US", Locale.US));

        // test message resolver
        assertEquals("foo_US", messageSource.getMessage(new DefaultMessageSourceResolvable("foo"), Locale.US));
    }
}
