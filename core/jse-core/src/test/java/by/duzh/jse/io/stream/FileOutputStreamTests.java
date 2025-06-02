package by.duzh.jse.io.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.logging.Logger;

import java.io.*;
import java.util.UUID;

public class FileOutputStreamTests {

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private String name;
    private File file;
    private static final Logger logger = Logger.getLogger(FileOutputStreamTests.class.getName());

    @BeforeEach
    public void init() {
        name = UUID.randomUUID() + ".txt";
        file = new File(TMP_DIR, name);
    }

    public void testCreate() {
        // With path
        String path = TMP_DIR + System.getProperty("file.separator") + name;
        try (OutputStream os = new FileOutputStream(path)) {
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        // With file
        try (OutputStream os = new FileOutputStream(file)) {
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
    }

    @Test
    public void testCreateAndAppend() {
        String s = "Hello";

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(s.getBytes());
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        try (OutputStream os = new FileOutputStream(file, true)) {
            os.write(s.getBytes());
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        byte[] bytes = new byte[30];
        int size = 0;
        try (InputStream is = new FileInputStream(file)) {
            size = is.read(bytes);
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
        Assertions.assertEquals(s + s, new String(bytes, 0, size));
    }

    @Test
    public void test() {
        try {
            // TODO: implement test
            logger.warning("WARNING!!! Test is not implemented yet!");
        } catch (Exception e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
    }
}