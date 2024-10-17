package by.duzh.jse.nio.file.attribute;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.JAVA_HOME_DIR;

public class BasicFileAttributesTest {
    private BasicFileAttributes dirAttributes, fileAttributes;

    @Before
    public void init() throws IOException {
        dirAttributes = Files.readAttributes(Paths.get(JAVA_HOME_DIR), BasicFileAttributes.class);
        fileAttributes = Files.readAttributes(Paths.get(FILE_PATH), BasicFileAttributes.class);
    }

    @Test
    public void test() {
        FileTime time = fileAttributes.creationTime();
        Assert.assertNotNull(time);

        Object fileKey = fileAttributes.fileKey();
        Assert.assertNull(fileKey);
        Assert.assertNull(dirAttributes.fileKey());

        Assert.assertFalse(fileAttributes.isDirectory());
        Assert.assertTrue(dirAttributes.isDirectory());

        Assert.assertFalse(fileAttributes.isOther());
        Assert.assertFalse(dirAttributes.isOther());

        Assert.assertTrue(fileAttributes.isRegularFile());
        Assert.assertFalse(dirAttributes.isRegularFile());

        Assert.assertFalse(fileAttributes.isSymbolicLink());
        Assert.assertFalse(dirAttributes.isSymbolicLink());

        Assert.assertNotNull(fileAttributes.lastAccessTime());
        Assert.assertNotNull(dirAttributes.lastAccessTime());

        Assert.assertNotNull(fileAttributes.lastModifiedTime());
        Assert.assertNotNull(dirAttributes.lastModifiedTime());
    }
}