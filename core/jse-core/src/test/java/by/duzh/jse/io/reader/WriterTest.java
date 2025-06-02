package by.duzh.jse.io.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.Writer;

public class WriterTest {

    private final char[] chars = "Hello".toCharArray();

    @Test
    public void testWrite() throws Exception {
        // char
        try (Writer writer = new CharArrayWriter()) {
            writer.write('a');
        }

        // chars buffer
        try (Writer writer = new CharArrayWriter()) {
            writer.write(chars);
        }

        // bytes buffer with offset and length
        try (Writer writer = new CharArrayWriter()) {
            writer.write(chars, 1, 3);
        }
    }

    @Test
    public void testAppendChars() throws Exception {
        // char
        try (Writer writer = new CharArrayWriter()) {
            Writer writer2 = writer.append('a');
            Assertions.assertEquals(writer, writer2);
        }

        // CharSequence
        try (Writer writer = new CharArrayWriter()) {
            CharSequence charSequence = "foo";
            Writer writer2 = writer.append(charSequence);
            Assertions.assertEquals(writer, writer2);
        }
    }

    @Test
    public void testFlush() throws Exception {
        try (Writer writer = new CharArrayWriter()) {
            writer.write('a');
            writer.flush();
        }
    }

    @Test
    public void testJDK11NullWriter() throws Exception {
        try (Writer writer = Writer.nullWriter()) {
            writer.write('a');
        }
    }
}