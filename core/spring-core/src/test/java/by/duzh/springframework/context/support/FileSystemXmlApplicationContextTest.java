package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemXmlApplicationContextTest {

    @Test
    void create() throws Exception {
        String path = new ClassPathResource(getClass().getPackageName().replace('.', '/') +
                "/app-context-foo.xml").getFile().getPath();

        try (var ctx = new FileSystemXmlApplicationContext(path)) {
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }

        try (var ctx = new FileSystemXmlApplicationContext()) {
            ctx.setConfigLocation(path);
            ctx.refresh();
            assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("foo"));
        }
    }
}