package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStreamTests {

    private final byte[] bytes = "Welcome to the Java(TM) Platform".getBytes();

    @Test
    public void testCreate() {
        InputStream is = new ByteArrayInputStream(bytes);
        try {
            Assert.assertEquals(bytes.length, is.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // We don't need close here

        is = new ByteArrayInputStream(bytes, 3, 4);
        try {
            Assert.assertEquals(4, is.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // We don't need close here
    }
}
