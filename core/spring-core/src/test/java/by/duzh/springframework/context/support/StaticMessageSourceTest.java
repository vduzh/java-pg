package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;

import java.util.HashMap;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://www.programcreek.com/java-api-examples/?api=org.springframework.context.support.StaticMessageSource
public class StaticMessageSourceTest {

    @Test
    void addMessage() throws Exception {
        StaticMessageSource messageSource = new StaticMessageSource();

        // one message
        messageSource.addMessage("foo", Locale.US, "foo_US");
        messageSource.addMessage("bar", Locale.US, "bar_US");

        // map of messages
        var map = new HashMap<String, String>();
        map.put("foo", "foo_DE");
        map.put("bar", "bar_DE");
        messageSource.addMessages(map, Locale.GERMANY);

        assertEquals("foo_US", messageSource.getMessage("foo", null, Locale.US));
        assertEquals("foo_DE", messageSource.getMessage("foo", null, Locale.GERMANY));

        assertEquals("bar_US", messageSource.getMessage("bar", null, Locale.US));
        assertEquals("bar_DE", messageSource.getMessage("bar", null, Locale.GERMANY));
    }
}