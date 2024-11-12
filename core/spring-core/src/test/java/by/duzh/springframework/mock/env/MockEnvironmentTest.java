package by.duzh.springframework.mock.env;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockEnvironmentTest {
    @Test
    void name() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty("foo", "bar");

        assertEquals("bar", env.getProperty("foo"));
    }
}
