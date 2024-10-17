package by.duzh.jse.io.stream;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//TODO: look into the implementation of the DataOutputStream
public class DataOutputStreamTests {

    @Test
    public void testWrite() {
        String s = "Hello";

        try (DataOutputStream os = new DataOutputStream(new ByteArrayOutputStream())) {
            os.writeDouble(1.1);
            os.writeBoolean(false);
            os.writeInt(123);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}