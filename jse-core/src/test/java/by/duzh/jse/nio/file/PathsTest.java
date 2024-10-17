package by.duzh.jse.nio.file;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.duzh.jse.nio.etc.Params.*;

public class PathsTest {

    @Test
    public void testGetWithStrings() {
        Path path = Paths.get(JAVA_HOME_DIR, FILE_NAME);
    }

    @Test
    public void testCreateWithURL() {
        Path path = Paths.get(new File(JAVA_HOME_DIR, FILE_NAME).toURI());
    }
}
