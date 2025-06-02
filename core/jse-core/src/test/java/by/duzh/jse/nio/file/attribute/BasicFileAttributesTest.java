package by.duzh.jse.nio.file.attribute;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.JAVA_HOME_DIR;

public class BasicFileAttributesTest {
    private BasicFileAttributes dirAttributes, fileAttributes;

    @BeforeEach
    public void init() throws IOException {
        dirAttributes = Files.readAttributes(Paths.get(JAVA_HOME_DIR), BasicFileAttributes.class);
        fileAttributes = Files.readAttributes(Paths.get(FILE_PATH), BasicFileAttributes.class);
    }

    @Test
    public void test() {
        FileTime time = fileAttributes.creationTime();
        Assertions.assertNotNull(time);

        Object fileKey = fileAttributes.fileKey();
        Assertions.assertNull(fileKey);
        Assertions.assertNull(dirAttributes.fileKey());

        Assertions.assertFalse(fileAttributes.isDirectory());
        Assertions.assertTrue(dirAttributes.isDirectory());

        Assertions.assertFalse(fileAttributes.isOther());
        Assertions.assertFalse(dirAttributes.isOther());

        Assertions.assertTrue(fileAttributes.isRegularFile());
        Assertions.assertFalse(dirAttributes.isRegularFile());

        Assertions.assertFalse(fileAttributes.isSymbolicLink());
        Assertions.assertFalse(dirAttributes.isSymbolicLink());

        Assertions.assertNotNull(fileAttributes.lastAccessTime());
        Assertions.assertNotNull(dirAttributes.lastAccessTime());

        Assertions.assertNotNull(fileAttributes.lastModifiedTime());
        Assertions.assertNotNull(dirAttributes.lastModifiedTime());
    }
}