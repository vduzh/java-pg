package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceTest {
    private final ResourceLoader loader = new ClassPathXmlApplicationContext();

    @Test
    void test() throws Exception {
        // TODO: use this technique in Java SE tests
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        Resource resource = new DefaultResourceLoader().getResource("file://" + file.getPath());
        assertTrue(resource.exists());
/*
        System.out.println(resource.getDescription());
        System.out.println(resource.getFile());
        System.out.println(resource.getFilename());
        System.out.println(resource.getURI());
        System.out.println(resource.getURL());
        //System.out.println(resource.getInputStream());
        System.out.println(resource.getClass());
*/

/*
        try (InputStream is = resource.getInputStream()) {
            assertTrue(is.available() > 0);
        }
*/

    }

    @Configuration
    static class AppConfig {
        @Value("classpath:test.txt")
        private Resource testTxt;

        @Bean
        public String xxx() {
            return testTxt.getFilename();
        }
    }

    @Test
    void valueAndResource() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ctx.getBean("xxx"));
    }
}
