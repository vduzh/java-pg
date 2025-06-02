package by.duzh.jse.io.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Formatter;

public class BufferedReaderTests {
    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final char[] chars = "Welcome to the Java(TM) Platform".toCharArray();

    @Test
    public void testCreate() {
        try (BufferedReader is = new BufferedReader(new CharArrayReader(chars))) {
            is.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader is = new BufferedReader(new CharArrayReader(chars), BUF_SIZE)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMarkSupported() {
        try (BufferedReader is = new BufferedReader(new CharArrayReader(chars))) {
            Assertions.assertTrue(is.markSupported());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLines() {
        // 3 lines text
        String s = new Formatter().format("foo %1$s bar %1$s buz %1$s", System.lineSeparator()).toString();

        try (BufferedReader is = new BufferedReader(new CharArrayReader(s.toCharArray()))) {
            Assertions.assertEquals(3, is.lines().count());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
