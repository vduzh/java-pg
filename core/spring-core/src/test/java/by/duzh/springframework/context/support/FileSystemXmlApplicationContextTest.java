package by.duzh.springframework.context.support;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemXmlApplicationContextTest {

    private static String absolutePath;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        var path = FileSystemXmlApplicationContextTest.class
                .getPackageName().replace('.', '/') + "/app-context-foo.xml";

        absolutePath = new ClassPathResource(path).getFile().getPath();
    }

    @Test
    void create() {
        try (var ctx = new FileSystemXmlApplicationContext(absolutePath)) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }

    @Test
    void setConfigLocation() {
        try (var ctx = new FileSystemXmlApplicationContext()) {
            ctx.setConfigLocation(absolutePath);
            ctx.refresh();
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }
}