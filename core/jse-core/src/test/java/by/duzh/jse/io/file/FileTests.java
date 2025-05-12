package by.duzh.jse.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

public class FileTests {
    private static final String JAVA_HOME_DIR = System.getProperty("java.home");
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    private static final String FILE_NAME = "LICENSE";

    private File file;

    @BeforeEach
    public void init() {
        file = new File(TMP_DIR);
    }

    @Test
    public void testCreate() {
        // With path
        file = new File(JAVA_HOME_DIR);
        Assertions.assertTrue(file.exists());

        // With path and name
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertTrue(file.exists());

        // With file and name
        file = new File(new File(JAVA_HOME_DIR), FILE_NAME);
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testCreateNewFile() throws IOException {
        file = new File(TMP_DIR + UUID.randomUUID() + ".txt");

        Assertions.assertTrue(file.createNewFile());
    }

    @Test
    public void testCreateDirectory() {
        file = new File(TMP_DIR + UUID.randomUUID());
        Assertions.assertTrue(file.mkdir());

        file = new File(TMP_DIR + UUID.randomUUID() + System.getProperty("file.separator") + UUID.randomUUID());
        Assertions.assertTrue(file.mkdirs());
    }

    @Test
    public void testExists() {
        file = new File(JAVA_HOME_DIR);
        Assertions.assertTrue(file.exists());

        file = new File(JAVA_HOME_DIR, "foo.txt");
        Assertions.assertFalse(file.exists());
    }

    @Test
    public void testGetAttributes() {
        // name
        file = new File(JAVA_HOME_DIR, "bin");
        Assertions.assertEquals("bin", file.getName());

        // length
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertTrue(file.length() > 0);

        // path
        file = new File(JAVA_HOME_DIR, "bin");
        Assertions.assertEquals(JAVA_HOME_DIR + System.getProperty("file.separator") + "bin", file.getPath());

        // absolute path
        file = new File(JAVA_HOME_DIR, "bin");
        Assertions.assertEquals(JAVA_HOME_DIR + System.getProperty("file.separator") + "bin", file.getAbsolutePath());

        // get parent
        file = new File(JAVA_HOME_DIR, "bin");
        Assertions.assertEquals(JAVA_HOME_DIR, file.getParent());

        // can read
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertTrue(file.canRead());

        // can write
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        // TODO: depends on the OS
        // Assertions.assertFalse(file.canWrite());

        // can execute
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        // TODO: depends on the OS
        //Assertions.assertFalse(file.canExecute());

        // is directory
        file = new File(JAVA_HOME_DIR);
        Assertions.assertTrue(file.isDirectory());

        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertFalse(file.isDirectory());

        // is file
        file = new File(JAVA_HOME_DIR);
        Assertions.assertFalse(file.isFile());

        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertTrue(file.isFile());

        // is hidden
        file = new File(JAVA_HOME_DIR, FILE_NAME);
        Assertions.assertFalse(file.isHidden());
    }

    @Test
    public void testList() {
        file = new File(JAVA_HOME_DIR);

        String[] list = file.list();
        Assertions.assertTrue(Arrays.asList(list).contains(FILE_NAME));

        list = file.list((file, name) -> FILE_NAME.equals(name));
        Assertions.assertEquals(1, list.length);
    }

    @Test
    public void testListFiles() {
        file = new File(JAVA_HOME_DIR);

        File[] list = file.listFiles();
        Assertions.assertTrue(Arrays.stream(list)
                .map(f -> f.getName())
                .anyMatch(name -> name.equals(FILE_NAME)));

        list = file.listFiles((file, name) -> FILE_NAME.equals(name));
        Assertions.assertEquals(1, list.length);

        list = file.listFiles((file) -> FILE_NAME.equals(file.getName()));
        Assertions.assertEquals(1, list.length);
    }

    @Test
    public void testSpace() throws IOException {
        file = new File(TMP_DIR);

        Assertions.assertTrue(file.getTotalSpace() > 0);
        Assertions.assertTrue(file.getFreeSpace() > 0);
        Assertions.assertTrue(file.getUsableSpace() > 0);
    }

    @Test
    public void testSetLastModified() throws IOException {
        file = new File(TMP_DIR + UUID.randomUUID() + ".txt");
        file.createNewFile();

        Assertions.assertTrue(file.setLastModified(System.currentTimeMillis()));
    }

    @Test
    public void testSetReadable() throws IOException {
        file = new File(TMP_DIR + UUID.randomUUID() + ".txt");
        file.createNewFile();

        Assertions.assertTrue(file.setReadable(true));
    }

    @Test
    public void testRename() throws IOException {
        file = new File(TMP_DIR + UUID.randomUUID() + ".txt");
        file.createNewFile();

        File file2 = new File(TMP_DIR + UUID.randomUUID() + ".dat");

        Assertions.assertTrue(file.renameTo(file2));
        Assertions.assertFalse(file.exists());
    }

    @Test
    public void testDelete() throws IOException {
        file = new File(TMP_DIR + UUID.randomUUID() + ".txt");
        file.createNewFile();

        Assertions.assertTrue(file.delete());
    }

    @Test
    public void testJDK7ToPath() {
        file = new File(JAVA_HOME_DIR + FILE_NAME);
        Path path = file.toPath();

        Assertions.assertEquals(file.getAbsolutePath(), path.toString());
    }
}
