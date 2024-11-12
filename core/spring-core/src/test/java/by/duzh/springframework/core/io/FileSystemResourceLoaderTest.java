package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemResourceLoaderTest {
    @Test
    void name() throws Exception {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource resource = resourceLoader.getResource(file.getPath());

        assertTrue(resource.getFile().exists());
    }
}
