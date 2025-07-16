package by.duzh.pg.app.spring.cloud.foo.util;

public final class FooMessageUtils {

	/**
	 * The context key for the message object.
	 */
	public static final String FAILED_MESSAGE_CONTEXT_KEY = "message";

	/**
	 * The context key for the message object.
	 */
	public static final String INPUT_MESSAGE_CONTEXT_KEY = "inputMessage";

	private FooMessageUtils() {
	}

	public static String foo(Integer i) {
		return "foo " + i;
	}
}
