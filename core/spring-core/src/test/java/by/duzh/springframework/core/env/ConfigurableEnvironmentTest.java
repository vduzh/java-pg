package by.duzh.springframework.core.env;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigurableEnvironmentTest {

    @Test
    void test() throws Exception {
        var ctx = new GenericXmlApplicationContext();
        ctx.refresh();

        ConfigurableEnvironment env = ctx.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();

        Map<String, Object> map = new HashMap<>();
        map.put("foo", "1");

        propertySources.addLast(new MapPropertySource("testMap", map));

        //ctx.close();

        // Java property available via env.
        assertNotNull(env.getProperty("user.home"));

        // Custom properties
        assertEquals("1", env.getProperty("foo"));

        map = new HashMap<>();
        map.put("foo", "2");

        propertySources.addFirst(new MapPropertySource("testMap2", map));

        // Custom properties
        assertEquals("2", env.getProperty("foo"));
    }

    @Test
    void name() throws Exception {
        System.out.println("Test not implemented: ConfigurableEnvironmentTest.name");
    }

    @Test
    public void testConfigurableEnvironment() {
        System.out.println("Test not implemented: ConfigurableEnvironmentTest.testConfigurableEnvironment");
    }
}
