package by.duzh.jse.nio.file;

import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

//TODO: Look into the details of the FileSystem class
public class FileSystemTest {
    private final FileSystem fs = FileSystems.getDefault();

    @Test
    public void testGetRootDirectories() {
        System.out.println(fs.getRootDirectories());
    }

    @Test
    public void testGetFileStores() {
        System.out.println(fs.getFileStores());
    }
}
