package by.duzh.springframework.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HierarchicalMessageSourceTest {
    private HierarchicalMessageSource messageSource;

    @BeforeAll
    public void setUp() throws Exception {
        StaticMessageSource parent = new StaticMessageSource();
        parent.addMessage("foo", Locale.US, "foo_US");

        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.setParentMessageSource(parent);
        messageSource.addMessage("bar", Locale.US, "bar_US");

        this.messageSource = messageSource;
    }

    @Test
    void test() throws Exception {
        // test message from parent
        assertEquals("foo_US", messageSource.getMessage("foo", null, Locale.US));
        // test message from child
        assertEquals("bar_US", messageSource.getMessage("bar", null, Locale.US));
    }
}
