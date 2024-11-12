package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.util.ResourceUtils.*;

public class DefaultResourceLoaderTest {

    @Test
    void test() throws Exception {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        // load a file resource
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        Resource resource = resourceLoader.getResource(FILE_URL_PREFIX + file.getPath());
        assertTrue(resource.exists());

        // load a file in class path
        String location = String.format("%s%s/test.txt", CLASSPATH_URL_PREFIX,
                ClassPathResourceTest.class.getPackageName().replace('.', '/'));
        resource = resourceLoader.getResource(location);
        assertTrue(resource.exists());

        // load via http
        resource = resourceLoader.getResource("http://www.google.com");
        assertTrue(resource.exists());
    }
}