package by.duzh.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ConfigurableApplicationContextTest {
    private ConfigurableApplicationContext ctx;

    @Test
    void registerShutdownHook() throws Exception {
        ctx = new GenericXmlApplicationContext(getClass(), "app-context-xml.xml");
        ctx.registerShutdownHook();
    }

    @Test
    void close() throws Exception {
        ctx = new GenericXmlApplicationContext(getClass(), "app-context-xml.xml");
        ctx.close();
    }
}
