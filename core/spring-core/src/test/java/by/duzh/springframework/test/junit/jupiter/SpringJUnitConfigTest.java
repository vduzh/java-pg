package by.duzh.springframework.test.junit.jupiter;

import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DefaultTestConfiguration.class)
public class SpringJUnitConfigTest {

    @Autowired
    String foo;

    @Test
    void test() {
        assertEquals("foo", foo);
    }
}
