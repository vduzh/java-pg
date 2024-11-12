package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

// TODO: look into the implementation of the the BufferedInputStream
public class BufferedInputStreamTests {
    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final byte[] bytes = "Welcome to the Java(TM) Platform".getBytes();

    @Test
    public void testCreate() {
        try (BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes), BUF_SIZE)) {
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
