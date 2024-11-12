package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

public class CharArrayReaderStreamTests {

    private final char[] chars = "Welcome to the Java(TM) Platform".toCharArray();

    @Test
    public void testCreate() {
        Reader reader = new CharArrayReader(chars);
        try {
            Assert.assertEquals(chars[0], reader.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // We don't need close here

        reader = new CharArrayReader(chars, 3, 4);
        try {
            Assert.assertEquals('c', reader.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // We don't need close here
    }
}
