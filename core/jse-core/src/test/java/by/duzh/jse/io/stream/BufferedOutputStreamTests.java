package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamTests {
    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final byte[] bytes = "Welcome to the Java(TM) Platform".getBytes();

    @Test
    public void testCreate() {
        try (BufferedOutputStream os = new BufferedOutputStream(new ByteArrayOutputStream())) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedOutputStream os = new BufferedOutputStream(new ByteArrayOutputStream(), BUF_SIZE)) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testToFlush() {
        // Manual flush
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); BufferedOutputStream os = new BufferedOutputStream(bos)) {
            os.write(bytes);
            Assert.assertEquals(0, bos.size());

            os.flush();
            Assert.assertEquals(bytes.length, bos.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Auto flush
        byte[] data;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (BufferedOutputStream os = new BufferedOutputStream(bos)) {
            os.write(bytes);
            data = bos.toByteArray();
            Assert.assertEquals(0, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        data = bos.toByteArray();
        Assert.assertEquals(bytes.length, data.length);
    }
}

