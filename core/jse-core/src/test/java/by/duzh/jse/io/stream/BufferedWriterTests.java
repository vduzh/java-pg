package by.duzh.jse.io.stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.IOException;

public class BufferedWriterTests {
    private static final Logger logger = Logger.getLogger(BufferedWriterTests.class.getName());

    // NOTE: Recommended buffer size
    private static final int BUF_SIZE = 8192;

    private final char[] chars = "Welcome to the Java(TM) Platform".toCharArray();

    @Test
    public void testCreate() {
        try (BufferedWriter os = new BufferedWriter(new CharArrayWriter())) {

        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        try (BufferedWriter os = new BufferedWriter(new CharArrayWriter(), BUF_SIZE)) {

        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
    }

    @Test
    public void testToFlush() {
        // Manual flush
        try (CharArrayWriter bos = new CharArrayWriter(); BufferedWriter os = new BufferedWriter(bos)) {
            os.write(chars);
            Assertions.assertEquals(0, bos.size());

            os.flush();
            Assertions.assertEquals(chars.length, bos.size());
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        // Auto flush
        char[] data;
        CharArrayWriter bos = new CharArrayWriter();

        try (BufferedWriter os = new BufferedWriter(bos)) {
            os.write(chars);
            data = bos.toCharArray();
            Assertions.assertEquals(0, data.length);
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }

        data = bos.toCharArray();
        Assertions.assertEquals(chars.length, data.length);
    }
}

