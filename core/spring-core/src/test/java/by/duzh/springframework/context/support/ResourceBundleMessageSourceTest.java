package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceBundleMessageSourceTest {
    @Test
    void test() throws Exception {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //messageSource.setBasename(");
        messageSource.setBasenames(
                "by/duzh/springframework/context/support/messages",
                "by/duzh/springframework/context/support/labels"
        );
        assertEquals("bar", messageSource.getMessage("foo", null, Locale.US));
        assertEquals("bar_DE", messageSource.getMessage("foo", null, Locale.GERMANY));

        assertEquals("enter", messageSource.getMessage("enter", null, Locale.US));
        assertEquals("enter_DE", messageSource.getMessage("enter", null, Locale.GERMANY));
    }
}
