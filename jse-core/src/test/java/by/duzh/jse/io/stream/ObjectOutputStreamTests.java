package by.duzh.jse.io.stream;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamTests {

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
            throw new RuntimeException(e);
        }
    }
}