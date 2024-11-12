package by.duzh.springframework.beans.factory.annotation;

import by.duzh.springframework.beans.factory.beans.Foo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImportResourceTest {
    @ImportResource(locations = {"classpath:context/app-context-legacy.xml"})
    public static class AppConfig {
    }

    private AnnotationConfigApplicationContext ctx;

    @BeforeAll
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void test() throws Exception {
        ctx.getBean("foo", Foo.class);
    }
}
