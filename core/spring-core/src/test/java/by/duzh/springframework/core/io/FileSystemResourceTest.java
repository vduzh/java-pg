package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.ResourceUtils.FILE_URL_PREFIX;

public class FileSystemResourceTest {
    @Test
    void test() throws Exception {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        Resource resource = new FileSystemResource(file.getPath());

        assertTrue(resource.getFile().exists());
    }
}
