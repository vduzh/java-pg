package by.duzh.jse.io.stream;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class OutputStreamTest {

    private final byte[] bytes = "Hello".getBytes();

    @Test
    public void testWrite() throws Exception {
        // byte
        try (OutputStream os = new ByteArrayOutputStream()) {
            os.write('a');
        }

        // bytes buffer
        try (OutputStream os = new ByteArrayOutputStream()) {
            os.write(bytes);
        }

        // bytes buffer with offset and length
        try (OutputStream os = new ByteArrayOutputStream()) {
            os.write(bytes, 1, 3);
        }
    }

    // TODO: look at the flash int different implementations
    @Test
    public void testFlush() throws Exception {
        try (OutputStream os = new ByteArrayOutputStream()) {
            os.write('a');
            os.flush();
        }
    }

    @Test
    public void testJDK11NullOutputStream() throws Exception {
        try (OutputStream os = OutputStream.nullOutputStream()) {
            os.write(32);
        }
    }
}