package by.duzh.jse.nio.file;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

import static by.duzh.jse.nio.etc.Params.*;

//TODO: look into the options of the methods
public class FilesTest {
    private Path dir, file;

    @Before
    public void init() {
        dir = Paths.get(TMP_DIR);
        file = Paths.get(TMP_DIR + UUID.randomUUID() + ".txt");
    }

    @Test
    public void testCreateDirectory() throws Exception {
        Path path = Paths.get(this.dir.toString(), UUID.randomUUID().toString());

        // without attributes
        Files.createDirectory(path);

        //TODO: with attributes
        //Files.createDirectory(path, );
    }

    @Test
    public void testIsDirectory() throws Exception {
        Assert.assertTrue(Files.isDirectory(dir));
        Assert.assertFalse(Files.isDirectory(Paths.get(dir.toString(), UUID.randomUUID().toString())));
    }

    @Test
    public void testCreateFile() throws Exception {
        // without attributes
        Files.createFile(file);

        //TODO: with attributes
        // Files.createFile(file, );
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertTrue(Files.size(Paths.get(FILE_PATH)) > 0);
    }

    @Test
    public void testDeleteFile() throws Exception {
        Files.createFile(file);

        Files.delete(file);
    }

    @Test
    public void testExists() throws Exception {
        Assert.assertFalse(Files.exists(file));

        Files.createFile(file);
        Assert.assertTrue(Files.exists(file));
    }

    @Test
    public void testNotExists() throws Exception {
        Assert.assertTrue(Files.notExists(file));

        Files.createFile(file);

        Assert.assertFalse(Files.notExists(file));
    }

    @Test
    public void testIsExecutable() throws Exception {
        Assert.assertFalse(Files.isExecutable(file));
    }

    @Test
    public void testIsHidden() throws Exception {
        Assert.assertFalse(Files.isHidden(Paths.get(JAVA_HOME_DIR, FILE_NAME)));
    }

    @Test
    public void testIsReadable() throws Exception {
        Assert.assertFalse(Files.isReadable(file));
    }

    @Test
    public void testIsRegularFile() throws Exception {
        Assert.assertTrue(Files.isRegularFile(Paths.get(JAVA_HOME_DIR, FILE_NAME)));
    }

    @Test
    public void testIsWritable() throws Exception {
        Assert.assertFalse(Files.isWritable(file));
    }

    @Test
    public void testCopy() throws Exception {
        // Without any copy option
        Path src = Paths.get(JAVA_HOME_DIR, FILE_NAME);
        Files.copy(src, file);

        // With a copy option
        Files.copy(src, file, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void testMove() throws Exception {
        Files.createFile(file);
        Path dest = Paths.get(TMP_DIR + UUID.randomUUID() + ".txt");

        Files.move(file, dest);

        Assert.assertFalse(Files.exists(file));
        Assert.assertTrue(Files.exists(dest));
    }

    @Test
    public void testWalkFileTree() throws Exception {
        throw new RuntimeException("testWalkFileTree not implemented yet!!!");
    }

    @Test
    public void testNewByteChannel() throws Exception {
        file = Paths.get(FILE_PATH);

        try (SeekableByteChannel channel = Files.newByteChannel(file)) {
            ;
        }
    }

    @Test
    // TODO: have a look at the DirectoryStream
    public void testNewDirectoryStream() throws Exception {
        file = Paths.get(JAVA_HOME_DIR);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file)) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assert.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }

        // Filter a directory with template
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file, "*.{txt, java}")) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assert.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }

        // Filter a directory with DirectoryStream Filter
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file, path -> "bin".equals(path.getFileName().toString()))) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assert.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }
    }

    @Test
    public void testNewInputStream() throws Exception {
        file = Paths.get(FILE_PATH);

        try (InputStream is = Files.newInputStream(file)) {
            Assert.assertTrue(is.available() > 0);
            ;
        }
    }

    @Test
    public void testNewOutputStream() throws Exception {
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(1);
        }
    }

    @Test
    public void testReadAttributes() throws Exception {
        file = Paths.get(FILE_PATH);
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

        Assert.assertFalse(attributes.isDirectory());
    }

    @Test
    public void testJDK11ReadString() throws Exception {
        var s = Files.readString(Paths.get(FILE_PATH));
        Assert.assertTrue(s.contains("JAVA_VERSION"));
    }

    @Test
    public void testJDK11WriteString() throws Exception {
        var res = Files.writeString(file, "foo");
    }
}
