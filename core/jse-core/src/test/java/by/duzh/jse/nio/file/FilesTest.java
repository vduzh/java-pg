package by.duzh.jse.nio.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static by.duzh.jse.nio.etc.Params.*;

//TODO: look into the options of the methods
public class FilesTest {
    private Path dir, file;

    @BeforeEach
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
        Assertions.assertTrue(Files.isDirectory(dir));
        Assertions.assertFalse(Files.isDirectory(Paths.get(dir.toString(), UUID.randomUUID().toString())));
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
        Assertions.assertTrue(Files.size(Paths.get(FILE_PATH)) > 0);
    }

    @Test
    public void testDeleteFile() throws Exception {
        Files.createFile(file);

        Files.delete(file);
    }

    @Test
    public void testExists() throws Exception {
        Assertions.assertFalse(Files.exists(file));

        Files.createFile(file);
        Assertions.assertTrue(Files.exists(file));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testNotExists() throws Exception {
        Assertions.assertTrue(Files.notExists(file));

        Files.createFile(file);

        Assertions.assertFalse(Files.notExists(file));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsExecutable() throws Exception {
        Assertions.assertFalse(Files.isExecutable(file));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsHidden() throws Exception {
        Assertions.assertFalse(Files.isHidden(Paths.get(JAVA_HOME_DIR, FILE_NAME)));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsReadable() throws Exception {
        Assertions.assertFalse(Files.isReadable(file));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsRegularFile() throws Exception {
        Assertions.assertTrue(Files.isRegularFile(Paths.get(JAVA_HOME_DIR, FILE_NAME)));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsWritable() throws Exception {
        Assertions.assertFalse(Files.isWritable(file));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCopy() throws Exception {
        // Without any copy option
        Path src = Paths.get(JAVA_HOME_DIR, FILE_NAME);
        Files.copy(src, file);

        // With a copy option
        Files.copy(src, file, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testMove() throws Exception {
        Files.createFile(file);
        Path dest = Paths.get(TMP_DIR + UUID.randomUUID() + ".txt");

        Files.move(file, dest);

        Assertions.assertFalse(Files.exists(file));
        Assertions.assertTrue(Files.exists(dest));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testWalkFileTree() throws Exception {
        System.out.println("Test not implemented: testWalkFileTree");
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testNewByteChannel() throws Exception {
        file = Paths.get(FILE_PATH);

        try (SeekableByteChannel channel = Files.newByteChannel(file)) {
            ;
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    // TODO: have a look at the DirectoryStream
    public void testNewDirectoryStream() throws Exception {
        file = Paths.get(JAVA_HOME_DIR);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file)) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assertions.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }

        // Filter a directory with template
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file, "*.{txt, java}")) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assertions.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }

        // Filter a directory with DirectoryStream Filter
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file, path -> "bin".equals(path.getFileName().toString()))) {
            for (Path path : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Assertions.assertNotNull(attributes.creationTime());
                //System.out.println(path.getFileName());
            }
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testNewInputStream() throws Exception {
        file = Paths.get(FILE_PATH);

        try (InputStream is = Files.newInputStream(file)) {
            Assertions.assertTrue(is.available() > 0);
            ;
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testNewOutputStream() throws Exception {
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(1);
        }
    }

    @Test
    public void testReadAttributes() throws Exception {
        file = Paths.get(FILE_PATH);
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

        Assertions.assertFalse(attributes.isDirectory());
    }

    @Test
    public void testJDK11ReadString() throws Exception {
        var s = Files.readString(Paths.get(FILE_PATH));
        Assertions.assertTrue(s.contains("JAVA_VERSION"));
    }

    @Test
    public void testJDK11WriteString() throws Exception {
        var res = Files.writeString(file, "foo");
    }
}
