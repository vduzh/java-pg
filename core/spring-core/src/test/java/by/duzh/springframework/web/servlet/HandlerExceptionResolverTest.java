package by.duzh.springframework.web.servlet;

import org.junit.jupiter.api.Test;

public class HandlerExceptionResolverTest {
    @Test
    void name() {
        throw new RuntimeException();
    }
}
