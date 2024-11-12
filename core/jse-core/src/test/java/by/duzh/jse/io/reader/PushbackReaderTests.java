package by.duzh.jse.io.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class PushbackReaderTests {
    private final char[] chars = "Welcome to the Java(TM) Platform".toCharArray();

    @Test
    public void testReturnOneCharToReader() {
        try (PushbackReader reader  = new PushbackReader(new CharArrayReader(chars))) {
            // Read a char
            int b = reader.read();
            // Return the char back to reader
            reader.unread(b);
            // Read that char again
            Assert.assertEquals(b, reader.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReturnMultipleCharsToReader() {
        try (PushbackReader reader = new PushbackReader(new CharArrayReader(chars), 8)) {
            char[] buffer = new char[4];
            // Read chars
            reader.read(buffer);
            String s = new String(buffer);
            // Return the chars back to IS
            reader.unread(buffer);
            // Read that bytes again
            reader.read(buffer);
            String s2 = new String(buffer);

            Assert.assertEquals(s, s2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}