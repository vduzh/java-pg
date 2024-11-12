package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.ResourceUtils.FILE_URL_PREFIX;

public class ResourceLoaderTest {
    @Test
    void test() throws Exception {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(FILE_URL_PREFIX + file.getPath());

        assertTrue(resource.exists());
    }
}
