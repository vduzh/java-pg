package by.duzh.springframework.core.env;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(ClassPathXmlApplicationContext.class)
@ActiveProfiles({"foo", "bar"})
public class EnvironmentTest {
    @Autowired
    private Environment env;

    @Test
    void test() throws Exception {
        assertEquals("[foo, bar]", Arrays.toString(env.getActiveProfiles()));
        assertEquals("[default]", Arrays.toString(env.getDefaultProfiles()));
    }
}
