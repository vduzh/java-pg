package by.duzh.jse.nio.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.duzh.jse.nio.etc.Params.*;

public class PathTest {
    private Path path;

    @BeforeEach
    public void init() {
        path = Paths.get(JAVA_HOME_DIR, FILE_NAME);
    }

    @Test
    public void testCreate() {
        path = Paths.get(JAVA_HOME_DIR, FILE_NAME);
        path = Paths.get(FILE_PATH);
    }

    @Test
    public void toFile() {
        File file = path.toFile();
        Assertions.assertEquals(file.getAbsolutePath(), path.toString());
    }

    @Test
    public void testGetFileName() {
        Assertions.assertEquals("release", path.getFileName().toString());
    }

    @Test
    public void testGetNameCount() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assertions.assertEquals(4, path.getNameCount());
    }

    @Test
    public void testGetNameByIndex() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assertions.assertEquals("Java", path.getName(0).toString());
        Assertions.assertEquals("jdk", path.getName(1).toString());
        Assertions.assertEquals("jre", path.getName(2).toString());
        Assertions.assertEquals("LICENSE", path.getName(3).toString());
    }

    @Test
    public void testGetEndsWith() {
        Assertions.assertTrue(path.endsWith("release"));
        Assertions.assertTrue(path.endsWith(Paths.get("release")));

        Assertions.assertFalse(path.endsWith("SE"));
        Assertions.assertFalse(path.endsWith(Paths.get("SE")));
    }

    @Test
    public void testGetParent() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assertions.assertEquals("jre", path.getParent().getFileName().toString());
    }

    @Test
    public void testGetRoot() {
        path = Paths.get("/Java/jdk/jre/LICENSE");
        Assertions.assertEquals("jre", path.getParent().getFileName().toString());
    }

    @Test
    public void testIsAbsolute() {
        Assertions.assertTrue(path.isAbsolute());

        path = Paths.get("/Java/jdk/jre/LICENSE");
        Assertions.assertFalse(path.isAbsolute());
    }

    @Test
    public void testToAbsolutepath() {
        path = Paths.get("/Java/jdk/jre/LICENSE");
        //System.out.println(path.toAbsolutePath());

        path = Paths.get("Java/jdk/jre/LICENSE");
        //System.out.println(path.toAbsolutePath());
    }

    @Test
    public void testResolve() {
        // Absolute path
        Path path1 = Paths.get("/Java/jdk/jre/LICENSE");
        Path resolvedPath = path1.resolve(path);
        Assertions.assertEquals(path, resolvedPath);

        // Relative path
        path1 = Paths.get(JAVA_HOME_DIR);
        resolvedPath = path1.resolve(Paths.get(FILE_NAME));
        Assertions.assertEquals(path, resolvedPath);
    }

    @Test
    public void testGetStartsWith() {
        path = Paths.get("Java/jdk/jre/LICENSE");

        Assertions.assertTrue(path.startsWith("Java"));
        Assertions.assertTrue(path.startsWith(Paths.get("Java")));

        Assertions.assertFalse(path.startsWith("Ja"));
        Assertions.assertFalse(path.startsWith(Paths.get("Ja")));
    }

    @Test
    public void testToString() {
        Assertions.assertEquals(FILE_PATH, path.toString());
    }
}