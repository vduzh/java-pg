package by.duzh.jse.nio.file.attribute;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.JAVA_HOME_DIR;

public class DosFileAttributesTest {
    private DosFileAttributes dirAttributes, fileAttributes;

    @Before
    public void init() throws IOException {
        dirAttributes = Files.readAttributes(Paths.get(JAVA_HOME_DIR), DosFileAttributes.class);
        fileAttributes = Files.readAttributes(Paths.get(FILE_PATH), DosFileAttributes.class);
    }

    @Test
    public void test() {
        Assert.assertTrue(fileAttributes.isArchive());//TODO: Why???
        Assert.assertFalse(dirAttributes.isArchive());

        Assert.assertFalse(fileAttributes.isHidden());
        Assert.assertFalse(dirAttributes.isHidden());

        Assert.assertFalse(fileAttributes.isReadOnly());
        Assert.assertFalse(dirAttributes.isReadOnly());

        Assert.assertFalse(fileAttributes.isSystem());
        Assert.assertFalse(dirAttributes.isSystem());
    }
}