package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

// TODO: look into the implementation of the the PushbackInputStream
public class PushbackInputStreamTests {
    private final byte[] bytes = "Welcome to the Java(TM) Platform".getBytes();

    @Test
    public void testReturnOneByteToIS() {
        try (PushbackInputStream is = new PushbackInputStream(new ByteArrayInputStream(bytes))) {
            // Read a byte
            int b = is.read();
            // Return the byte back to IS
            is.unread(b);
            // Read that byte again
            Assert.assertEquals(b, is.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReturnMultipleBytesToIS() {
        try (PushbackInputStream is = new PushbackInputStream(new ByteArrayInputStream(bytes), 8)) {
            byte[] bytes = new byte[4];
            // Read bytes
            is.read(bytes);
            String s = new String(bytes);
            // Return the bytes back to IS
            is.unread(bytes);
            // Read that bytes again
            is.read(bytes);
            String s2 = new String(bytes);

            Assert.assertEquals(s, s2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}