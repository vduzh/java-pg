package by.duzh.jse.io.stream;

import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class ObjectOutputStreamTests {
    private static final Logger logger = Logger.getLogger(ObjectOutputStreamTests.class.getName());

    private final byte[] bytes = "Hello".getBytes();

    @Test
    public void testWrites() {
        try (ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.writeBoolean(false);
            output.writeByte(bytes[0]);
            output.writeBytes("foo");
            output.writeChar('f');
            output.writeDouble(1.2);
            output.writeFloat(1.1f);
            output.writeLong(11);
            output.writeShort(1);

            //output.writeObject(new User(1, "foo"));
        } catch (IOException e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
    }

    @Test
    public void test() {
        try {
            // TODO: implement test
            logger.warning("WARNING!!! Test is not implemented yet!");
        } catch (Exception e) {
            logger.warning("WARNING!!! Test is not implemented yet!");
        }
    }
}