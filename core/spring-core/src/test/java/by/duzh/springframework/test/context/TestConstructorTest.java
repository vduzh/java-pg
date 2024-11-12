package by.duzh.springframework.test.context;

import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {DefaultTestConfiguration.class}) // Must present!
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ANNOTATED)
public class TestConstructorTest {
    private String foo;

    public TestConstructorTest(@Autowired String foo) {
        this.foo = foo;
    }

    @Test
    void name() {
        assertEquals("foo", foo);
    }
}
