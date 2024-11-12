package by.duzh.jse.nio.file;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.duzh.jse.nio.etc.Params.*;

public class PathTest {
    private Path path;

    @Before
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
        Assert.assertEquals(file.getAbsolutePath(), path.toString());
    }

    @Test
    public void testGetFileName() {
        Assert.assertEquals("LICENSE", path.getFileName().toString());
    }

    @Test
    public void testGetNameCount() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assert.assertEquals(4, path.getNameCount());
    }

    @Test
    public void testGetNameByIndex() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assert.assertEquals("Java", path.getName(0).toString());
        Assert.assertEquals("jdk", path.getName(1).toString());
        Assert.assertEquals("jre", path.getName(2).toString());
        Assert.assertEquals("LICENSE", path.getName(3).toString());
    }

    @Test
    public void testGetEndsWith() {
        Assert.assertTrue(path.endsWith("LICENSE"));
        Assert.assertTrue(path.endsWith(Paths.get("LICENSE")));

        Assert.assertFalse(path.endsWith("SE"));
        Assert.assertFalse(path.endsWith(Paths.get("SE")));
    }

    @Test
    public void testGetParent() {
        path = Paths.get("/Java/jdk/jre/LICENSE");

        Assert.assertEquals("jre", path.getParent().getFileName().toString());
    }

    @Test
    public void testGetRoot() {
        path = Paths.get("/Java/jdk/jre/LICENSE");
        Assert.assertEquals("jre", path.getParent().getFileName().toString());
    }

    @Test
    public void testIsAbsolute() {
        Assert.assertTrue(path.isAbsolute());

        path = Paths.get("/Java/jdk/jre/LICENSE");
        Assert.assertFalse(path.isAbsolute());
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
        Assert.assertEquals(path, resolvedPath);

        // Relative path
        path1 = Paths.get(JAVA_HOME_DIR);
        resolvedPath = path1.resolve(Paths.get(FILE_NAME));
        Assert.assertEquals(path, resolvedPath);
    }

    @Test
    public void testGetStartsWith() {
        path = Paths.get("Java/jdk/jre/LICENSE");

        Assert.assertTrue(path.startsWith("Java"));
        Assert.assertTrue(path.startsWith(Paths.get("Java")));

        Assert.assertFalse(path.startsWith("Ja"));
        Assert.assertFalse(path.startsWith(Paths.get("Ja")));
    }

    @Test
    public void testToString() {
        Assert.assertEquals(FILE_PATH, path.toString());
    }
}