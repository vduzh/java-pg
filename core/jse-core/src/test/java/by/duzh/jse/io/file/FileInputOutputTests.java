package by.duzh.jse.io.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileInputOutputTests {
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    @Test
    public void testRead() {
        String name = "C:\\1\\data.txt";
        try (FileInputStream fis = new FileInputStream(name)) {
            do {
                int i = fis.read();
                if (i == -1) break;
                Assert.assertTrue('a' == (char) i || 'b' == (char) i || i == 13 || i == 10);
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFileNotFoundError() {
        try (FileInputStream fis = new FileInputStream(TMP_DIR + UUID.randomUUID().toString() + ".txt")) {
            ;
        } catch (IOException e) {

        }
    }

    @Test
    public void testWriteToFileClassicOK() {
        FileOutputStream fos = null;
        try {
            String name = TMP_DIR + UUID.randomUUID().toString() + ".txt";
            fos = new FileOutputStream(name);
            fos.write("Test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testWriteToFileARMOK() {
        String name = TMP_DIR + UUID.randomUUID().toString() + ".txt";
        try (FileOutputStream fos = new FileOutputStream(name)) {
            fos.write("Test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
