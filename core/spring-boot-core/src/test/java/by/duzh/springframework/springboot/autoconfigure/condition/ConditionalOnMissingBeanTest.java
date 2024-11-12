package by.duzh.springframework.springboot.autoconfigure.condition;

import org.junit.jupiter.api.Test;

public class ConditionalOnMissingBeanTest {
    @Test
    void name() {
        throw new RuntimeException();
    }
}
