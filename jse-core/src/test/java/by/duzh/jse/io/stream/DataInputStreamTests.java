package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

//TODO: look into the implementation of the DataInputStream
public class DataInputStreamTests {
    private byte[] bytes;

    @Before
    public void init() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); DataOutputStream os = new DataOutputStream(bos)) {
            os.writeDouble(1.1);
            os.writeFloat(1.1f);
            os.writeBoolean(false);
            os.writeInt(123);
            os.writeLong(123);

            bytes = bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRead() {
        try (DataInputStream is = new DataInputStream(new ByteArrayInputStream(bytes))) {
            Assert.assertEquals(1.1, is.readDouble(), 0.1);
            Assert.assertEquals(1.1f, is.readFloat(), 0.1);
            Assert.assertFalse(is.readBoolean());
            Assert.assertEquals(123, is.readInt());
            Assert.assertEquals(123, is.readLong());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}