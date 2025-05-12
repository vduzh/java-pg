package by.duzh.jse.io;

import by.duzh.jse.io.etc.User;
import org.junit.Test;
import java.util.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ObjectOutputTests {

    private final byte[] bytes = "Hello".getBytes();

    @Test
    public void testWriteObject() {
        try (ObjectOutput output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.writeObject(new User(1, "foo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWrite() {
        // byte
        try (ObjectOutput output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.write(bytes[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // bytes buffer
        try (ObjectOutput output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // bytes buffer with offset and length
        try (ObjectOutput output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.write(bytes, 1, 3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFlush() {
        try (ObjectOutput output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            output.write(bytes[0]);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}