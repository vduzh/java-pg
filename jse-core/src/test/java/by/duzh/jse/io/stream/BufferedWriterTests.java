package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.IOException;

public class BufferedWriterTests {
    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final char[] chars = "Welcome to the Java(TM) Platform".toCharArray();

    @Test
    public void testCreate() {
        try (BufferedWriter os = new BufferedWriter(new CharArrayWriter())) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter os = new BufferedWriter(new CharArrayWriter(), BUF_SIZE)) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testToFlush() {
        // Manual flush
        try (CharArrayWriter bos = new CharArrayWriter(); BufferedWriter os = new BufferedWriter(bos)) {
            os.write(chars);
            Assert.assertEquals(0, bos.size());

            os.flush();
            Assert.assertEquals(chars.length, bos.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Auto flush
        char[] data;
        CharArrayWriter bos = new CharArrayWriter();

        try (BufferedWriter os = new BufferedWriter(bos)) {
            os.write(chars);
            data = bos.toCharArray();
            Assert.assertEquals(0, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        data = bos.toCharArray();
        Assert.assertEquals(chars.length, data.length);
    }
}

