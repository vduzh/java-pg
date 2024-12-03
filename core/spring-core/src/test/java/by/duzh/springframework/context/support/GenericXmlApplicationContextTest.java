package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.env.MockEnvironment;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericXmlApplicationContextTest {
    static final String PATH = GenericXmlApplicationContextTest.class
            .getPackageName().replace('.', '/') + "/app-context-foo.xml";

    @Test
    void createAndLoadWithClassPathResource() {
        try (GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()) {
            ctx.load(new ClassPathResource(PATH));
            ctx.refresh();
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void createAndLoadWithClasspath() {
        try (GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()) {
            ctx.load("classpath:" + PATH);
            ctx.refresh();
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void createAndLoadWithRelativeClassAndResourceNames() {
        try (GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()) {
            ctx.load(getClass(), "app-context-foo.xml");
            ctx.refresh();
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void createWithClassPathResourceInConstructor() {
        try (var ctx = new GenericXmlApplicationContext(new ClassPathResource(PATH))) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void createWithPathInConstructor() {
        try (var ctx = new GenericXmlApplicationContext(PATH)) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void createWithRelativeClassAndResourceNamesInConstructor() {
        try (var ctx = new GenericXmlApplicationContext(getClass(), "app-context-foo.xml")) {
            ctx.getBean("foo");
        }
    }

    @Test
    void getReader() throws Exception {
        try (var ctx = new GenericXmlApplicationContext(getClass(), "app-context-foo.xml")) {
            XmlBeanDefinitionReader reader = ctx.getReader();
        }
    }

    @Test
    void setEnvironment() throws Exception {
        try (var ctx = new GenericXmlApplicationContext(getClass(), "app-context-foo.xml")) {
            MockEnvironment env = new MockEnvironment();
            env.setProperty("foo", "bar");
            ctx.setEnvironment(env);
        }
    }

    @Test
    void getMessage() throws Exception {
        try (var ctx = new GenericXmlApplicationContext(getClass(), "app-context-xml.xml")) {
            assertEquals("bar_DE", ctx.getMessage("foo", null, Locale.GERMANY));
        }
    }
}
