package by.duzh.jse.io.file;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.UUID;

//TODO: look into the implementation of the RandomAccessFile
public class RandomAccessFileTests {
    private static final String JAVA_HOME_DIR = System.getProperty("java.home");
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    private static final String FILE_NAME = "LICENSE";

    @Test
    public void testRead() {
        File file = new File(JAVA_HOME_DIR, FILE_NAME);
        try (RandomAccessFile ras = new RandomAccessFile(file, "r")) {
            ras.seek(16);
            Assert.assertTrue(ras.read() > -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWrite() {
        File file = new File(TMP_DIR, UUID.randomUUID() + ".txt");

        try (RandomAccessFile ras = new RandomAccessFile(file, "rws")) {
            ras.writeInt(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSetLength() {
        File file = new File(TMP_DIR, UUID.randomUUID() + ".txt");

        try (RandomAccessFile ras = new RandomAccessFile(file, "rws")) {
            ras.setLength(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}