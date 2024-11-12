package by.duzh.springframework.test.context;

import by.duzh.springframework.test.ChildTestConfiguration;
import by.duzh.springframework.test.DefaultTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class) // use it to have app context
@ContextHierarchy({
        @ContextConfiguration(classes = DefaultTestConfiguration.class),
        @ContextConfiguration(classes = ChildTestConfiguration.class)
})
public class ContextHierarchyTest {
    @Autowired
    String foo;

    @Autowired
    String bar;

    @Test
    void test() {
        assertEquals("foo", foo);
        assertEquals("bar", bar);
    }
}
