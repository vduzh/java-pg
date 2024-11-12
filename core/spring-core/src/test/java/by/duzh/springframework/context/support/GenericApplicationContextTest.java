package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class GenericApplicationContextTest {
    @Test
    void test() throws Exception {
        // Load definition from XML file
        Resource resource = new ClassPathResource(getClass().getPackageName().replace('.', '/') + "/app-context-foo.xml");

        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(resource);
        ctx.refresh();

        ctx.getBean("foo");
    }
}
