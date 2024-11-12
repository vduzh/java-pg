package by.duzh.springframework.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(AnnotationConfigContextLoader.class)
@TestPropertySource("test.properties")
@TestPropertySource(properties = {"color=red", "port: 4242"})
@TestPropertySource // classpath:by/duzh/springframework/test/context/TestPropertySourceTest.properties
public class TestPropertySourceTest {

    @Test
    void name(@Autowired Environment env) {
        assertEquals("guest", env.getProperty("user"));

        assertEquals("red", env.getProperty("color"));
        assertEquals("4242", env.getProperty("port"));

        assertEquals("Do it", env.getProperty("just"));
    }
}
