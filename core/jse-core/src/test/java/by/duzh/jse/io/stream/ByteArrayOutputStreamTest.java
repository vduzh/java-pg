package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ByteArrayOutputStreamTest {
    private ByteArrayOutputStream os;
    private final String TEST_STRING = "Hello";

    @Test
    public void testCreate() throws Exception {
        os = new ByteArrayOutputStream();
        os = new ByteArrayOutputStream(64);
        // there is no need to close this kind of stream
    }

    @Test
    public void testToString() throws Exception {
        os = new ByteArrayOutputStream();
        os.write(TEST_STRING.getBytes());

        Assert.assertEquals(TEST_STRING, os.toString());
        Assert.assertEquals(TEST_STRING, os.toString(Charset.defaultCharset())); //  JDK10
    }

    @Test
    public void testWriteTo() throws Exception {
        os = new ByteArrayOutputStream();
        os.write(TEST_STRING.getBytes());

        var os2 = new ByteArrayOutputStream();
        os.writeTo(os2);

        Assert.assertEquals(TEST_STRING, os2.toString());
    }

    @Test
    public void testJDK11WriteBytes() throws Exception {
        os = new ByteArrayOutputStream();
        os.writeBytes(TEST_STRING.getBytes());

        Assert.assertEquals(TEST_STRING, os.toString());
    }
}