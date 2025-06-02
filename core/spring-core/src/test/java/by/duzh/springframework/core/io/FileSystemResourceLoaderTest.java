package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import java.util.logging.Logger;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemResourceLoaderTest {
    private static final Logger logger = Logger.getLogger(FileSystemResourceLoaderTest.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    void testFileSystemResource() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    void name() throws Exception {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();

        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource resource = resourceLoader.getResource(file.getPath());

        assertTrue(resource.getFile().exists());
    }
}
