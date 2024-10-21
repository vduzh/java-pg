package by.duzh.jse.lang;

import org.junit.Test;

public class ExceptionTest {

    @Test
    public void testCustomException() throws Exception {
        class CustomException extends Exception {
            public CustomException() {
                super();
            }

            public CustomException(String message) {
                super(message);
            }

            public CustomException(String message, Throwable cause) {
                super(message, cause);
            }

            public CustomException(Throwable cause) {
                super(cause);
            }

            protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
                super(message, cause, enableSuppression, writableStackTrace);
            }
        }

        try {
            throw new CustomException("foo");
        } catch (CustomException e) {
            System.out.println(e);
        }

    }
}
