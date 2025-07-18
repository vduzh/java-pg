package by.duzh.pg.app.spring.cloud.foo.util;

import java.util.UUID;

public final class MessageUtils {

    /**
     * The context key for the message object.
     */
    public static final String FAILED_MESSAGE_CONTEXT_KEY = "message";

    /**
     * The context key for the message object.
     */
    public static final String INPUT_MESSAGE_CONTEXT_KEY = "inputMessage";

    private MessageUtils() {
    }

    public static String foo(Integer i) {
        return "foo " + i;
    }

    public static String messageId() {
        return UUID.randomUUID().toString();
    }
}
