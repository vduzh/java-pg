package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.env.MockEnvironment;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericXmlApplicationContextTest {

    @Test
    void create() throws Exception {
        String path = getClass().getPackageName().replace('.', '/') + "/app-context-foo.xml";

        // Default constructor
        try (GenericXmlApplicationContext ctx = new GenericXmlApplicationContext()) {
            ctx.load(new ClassPathResource(path));
            ctx.refresh();
            ctx.getBean("foo");
        }

        try (var ctx = new GenericXmlApplicationContext()) {
            ctx.load("classpath:" + path);
            ctx.refresh();
            ctx.getBean("foo");
        }

        try (var ctx = new GenericXmlApplicationContext()) {
            //NOTE: relativeClass is very useful
            ctx.load(getClass(), "app-context-foo.xml");
            ctx.refresh();
            ctx.getBean("foo");
        }

        // Constructors with params
        try (var ctx = new GenericXmlApplicationContext(new ClassPathResource(path))) {
            ctx.getBean("foo");
        }

        try (var ctx = new GenericXmlApplicationContext(path)) {
            ctx.getBean("foo");
        }

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
