package by.duzh.jse.io.reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.UUID;

public class FileWriterTests {

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
        try (Writer os = new FileWriter(path)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // With file
        try (Writer os = new FileWriter(file)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateAndAppend() {
        String s = "Hello";

        try (Writer os = new FileWriter(file)) {
            os.write(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Writer os = new FileWriter(file, true)) {
            os.write(s.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[] chars = new char[30];
        int size = 0;
        try (FileReader reader = new FileReader(file)) {
            size = reader.read(chars);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(s + s, new String(chars, 0, size));
    }
}