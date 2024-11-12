package by.duzh.springframework.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.support.DefaultTestContextBootstrapper;

@BootstrapWith(DefaultTestContextBootstrapper.class)
public class BootstrapWithTest {
    @Test
    void name() {
        throw new RuntimeException();
    }
}
