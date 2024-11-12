package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.UUID;

public class FileOutputStreamTests {

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private String name;
    private File file;

    @Before
    public void init() {
        name = UUID.randomUUID() + ".txt";
        file = new File(TMP_DIR, name);
    }

    public void testCreate() {
        // With path
        String path = TMP_DIR + System.getProperty("file.separator") + name;
        try (OutputStream os = new FileOutputStream(path)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // With file
        try (OutputStream os = new FileOutputStream(file)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateAndAppend() {
        String s = "Hello";

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(s.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (OutputStream os = new FileOutputStream(file, true)) {
            os.write(s.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = new byte[30];
        int size = 0;
        try (InputStream is = new FileInputStream(file)) {
            size = is.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(s + s, new String(bytes, 0, size));
    }
}