package by.duzh.jse.io.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.CharArrayWriter;
import java.io.IOException;

public class CharArrayWriterTests {
    private CharArrayWriter writer;

    @Test
    public void testCreate() {
        writer = new CharArrayWriter();
        writer = new CharArrayWriter(64);
        // there is no need to close this kind of stream
    }

    @Test
    public void testToString() {
        String s = "Hello";

        writer = new CharArrayWriter();
        try {
            writer.write(s.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // there is no need to close this kind of stream

        Assert.assertEquals(s, writer.toString());
    }

    @Test
    public void testWriteTo() {
        String s = "Hello";

        writer = new CharArrayWriter();
        try {
            writer.write(s.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CharArrayWriter os2 = new CharArrayWriter();
        try {
            writer.writeTo(os2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(s, os2.toString());
    }
}