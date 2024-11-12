package by.duzh.jse.nio.file;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

//TODO: have a look at the FileSystems class
public class FileSystemsTest {
    @Test
    public void test() {
        FileSystem fs = FileSystems.getDefault();
        Assert.assertNotNull(fs);
    }
}
