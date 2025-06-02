package by.duzh.jse.io.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;

import static by.duzh.jse.io.etc.Params.FILE_NAME;
import static by.duzh.jse.io.etc.Params.JAVA_HOME_DIR;

public class ReaderTest {
    private final File file = new File(JAVA_HOME_DIR, FILE_NAME);

    private final String TEXT = "Welcome to the Java(TM) Platform";
    private final char[] chars = TEXT.toCharArray();

    @Test
    public void testRead() throws Exception {
        // char by char
        try (Reader reader = new CharArrayReader(chars)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            Assertions.assertEquals(TEXT, sb.toString());
        }

        // char buffer
        try (Reader reader = new CharArrayReader(chars)) {
            StringBuilder sb = new StringBuilder();

            char[] buffer = new char[8];
            int num;

            while ((num = reader.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, num));
            }

            Assertions.assertEquals(TEXT, sb.toString());
        }

        // chars buffer with offset and length
        try (Reader reader = new CharArrayReader(chars)) {
            StringBuilder sb = new StringBuilder();

            char[] buffer = new char[8];
            buffer[0] = '!';
            int num;

            while ((num = reader.read(buffer, 1, 6)) != -1) {
                //System.out.print(new String(buffer, 0, num + 1));
            }
        }
    }

    @Test
    public void testSkip() throws Exception {
        try (Reader reader = new CharArrayReader(chars)) {
            Assertions.assertEquals(5, reader.skip(5));
            Assertions.assertEquals('m', (char) reader.read());

            Assertions.assertEquals(6, reader.skip(6));
            Assertions.assertEquals('h', (char) reader.read());

            Assertions.assertEquals(TEXT.length() - 5 - 1 - 6 - 1, reader.skip(1000));
        }
    }

    @Test
    public void testMarkSupported() throws Exception {
        try (Reader reader = new FileReader(file)) {
            Assertions.assertFalse(reader.markSupported());
        }

        try (Reader reader = new CharArrayReader(chars)) {
            Assertions.assertTrue(reader.markSupported());
        }
    }

    @Test
    public void testMarkAndReset() throws Exception {
        try (Reader reader = new CharArrayReader(chars)) {
            reader.skip(2);

            reader.mark(1000);
            Assertions.assertEquals('l', (char) reader.read());
            Assertions.assertEquals('c', (char) reader.read());

            reader.reset();
            Assertions.assertEquals('l', (char) reader.read());
        }
    }

    @Test
    public void testJDK10TransferTo() throws Exception {
        try (Reader reader = new CharArrayReader(chars); Writer writer = new CharArrayWriter()) {
            reader.transferTo(writer);
            Assertions.assertEquals(TEXT, writer.toString());
        }
    }

    @Test
    public void testJDK11NullReader() throws Exception {
        try (Reader reader = Reader.nullReader()) {
            Assertions.assertEquals(-1, reader.read());
        }
    }
}
