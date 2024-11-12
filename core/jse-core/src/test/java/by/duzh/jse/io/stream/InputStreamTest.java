package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static by.duzh.jse.io.etc.Params.FILE_NAME;
import static by.duzh.jse.io.etc.Params.JAVA_HOME_DIR;

public class InputStreamTest {
    private final File file = new File(JAVA_HOME_DIR, FILE_NAME);

    private final String s = "Welcome to the Java(TM) Platform";
    private final byte[] bytes = s.getBytes();

    @Test
    public void testAvailable() throws Exception {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Assert.assertEquals(bytes.length, is.available());
        }
    }

    @Test
    public void testRead() throws Exception {
        // byte by byte
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            byte[] res = new byte[is.available()];
            for (int b = is.read(), i = 0; b != -1; b = is.read(), i++) {
                res[i] = (byte) b;
            }
            Assert.assertArrayEquals(bytes, res);
        }

        // bytes buffer
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            byte[] res = new byte[is.available()];
            byte[] buffer = new byte[8];
            for (int length = is.read(buffer), pos = 0; length != -1; length = is.read(buffer)) {
                System.arraycopy(buffer, 0, res, pos, length);
                pos += length;
            }
            Assert.assertArrayEquals(bytes, res);
        }

        // bytes buffer with offset and length
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            byte[] buffer = {100, 101, 0, 0, 0, 0, 0, 0};
            is.read(buffer, 2, 6);
            byte[] expected = {100, 101, 87, 101, 108, 99, 111, 109};
            Assert.assertArrayEquals(expected, buffer);
        }
    }

    @Test
    public void testSkip() throws Exception {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Assert.assertEquals(5, is.skip(5));
            Assert.assertEquals('m', (char) is.read());

            Assert.assertEquals(6, is.skip(6));
            Assert.assertEquals('h', (char) is.read());

            Assert.assertEquals(s.length() - 5 - 1 - 6 - 1, is.skip(1000));
        }
    }

    @Test
    public void testMarkSupported() throws Exception {
        try (InputStream is = new FileInputStream(file)) {
            Assert.assertFalse(is.markSupported());
        }

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Assert.assertTrue(is.markSupported());
        }
    }

    @Test
    public void testMarkAndReset() throws Exception {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            is.skip(2);

            is.mark(1000);
            Assert.assertEquals('l', (char) is.read());
            Assert.assertEquals('c', (char) is.read());

            is.reset();
            Assert.assertEquals('l', (char) is.read());
        }
    }

    @Test
    public void testJDK9TransferTo() throws Exception {
        try (InputStream is = new ByteArrayInputStream(bytes); ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            is.transferTo(os);
            Assert.assertArrayEquals(bytes, os.toByteArray());
        }
    }

    @Test
    public void testJDK11NullInputStream() throws Exception {
        try (InputStream is = InputStream.nullInputStream()) {
            Assert.assertEquals(0, is.available());
            Assert.assertEquals(-1, is.read());
        }
    }
}
