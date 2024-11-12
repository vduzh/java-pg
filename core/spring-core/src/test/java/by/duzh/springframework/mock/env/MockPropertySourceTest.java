package by.duzh.springframework.mock.env;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockPropertySourceTest {
    @Test
    void name() {
        MockPropertySource propertySource = new MockPropertySource();
        propertySource.setProperty("foo", "bar");

        assertEquals("bar", propertySource.getProperty("foo"));
    }
}
