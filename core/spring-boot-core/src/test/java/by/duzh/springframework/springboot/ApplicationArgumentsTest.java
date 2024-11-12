package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DefaultSpringBootConfiguration.class, args = {
        "--app.test=test", "--app.test=test2",
        "--app.test3=test3",
        "foo", "bar" // non-options
})
public class ApplicationArgumentsTest {

    @Test
    void test(@Autowired ApplicationArguments args) {
        assertTrue(args.containsOption("app.test"));
        List<String> values = args.getOptionValues("app.test");
        assertEquals(2, values.size());

        values = args.getOptionValues("app.test3");
        assertEquals("test3", values.get(0));

        assertTrue(args.getNonOptionArgs().containsAll(Arrays.asList("foo", "bar")));
    }
}
