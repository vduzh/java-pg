package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

// TODO: look into the implementation of the the SequenceInputStream
public class SequenceInputStreamTests {
    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final byte[] bytes = "Welcome to the Java(TM) Platform".getBytes();

    @Test
    public void testCreate() {
        try (ByteArrayInputStream is1 = new ByteArrayInputStream("Hello ".getBytes());
             ByteArrayInputStream is2 = new ByteArrayInputStream("World!".getBytes());
             SequenceInputStream is = new SequenceInputStream(is1, is2)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateWithEnumerationOfInputStreams() {
        Vector<ByteArrayInputStream> vector = new Vector<>();
        vector.add(new ByteArrayInputStream("Hello ".getBytes()));
        vector.add(new ByteArrayInputStream("World!".getBytes()));

        try (SequenceInputStream is = new SequenceInputStream(vector.elements())) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = is.read()) != -1) {
                sb.append((char)i);
            }
            Assert.assertEquals("Hello World!", sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRead() {
        try (ByteArrayInputStream is1 = new ByteArrayInputStream("Hello ".getBytes());
             ByteArrayInputStream is2 = new ByteArrayInputStream("World!".getBytes());
             SequenceInputStream is = new SequenceInputStream(is1, is2)) {

            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = is.read()) != -1) {
                sb.append((char)i);
            }
            Assert.assertEquals("Hello World!", sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMarkSupported() {
        try (BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
            Assert.assertTrue(is.markSupported());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}