package by.duzh.jse.nio.file.attribute;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.JAVA_HOME_DIR;

public class DosFileAttributesTest {
    private DosFileAttributes dirAttributes, fileAttributes;

    @BeforeEach
    public void init() throws IOException {
        dirAttributes = Files.readAttributes(Paths.get(JAVA_HOME_DIR), DosFileAttributes.class);
        fileAttributes = Files.readAttributes(Paths.get(FILE_PATH), DosFileAttributes.class);
    }

    @Test
    public void test() {
        Assertions.assertTrue(fileAttributes.isArchive());//TODO: Why???
        Assertions.assertFalse(dirAttributes.isArchive());

        Assertions.assertFalse(fileAttributes.isHidden());
        Assertions.assertFalse(dirAttributes.isHidden());

        Assertions.assertFalse(fileAttributes.isReadOnly());
        Assertions.assertFalse(dirAttributes.isReadOnly());

        Assertions.assertFalse(fileAttributes.isSystem());
        Assertions.assertFalse(dirAttributes.isSystem());
    }
}