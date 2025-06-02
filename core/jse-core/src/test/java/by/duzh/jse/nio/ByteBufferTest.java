package by.duzh.jse.nio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufferTest {
    private static final String TEXT = "1234567890"; // length = 10
    private static final byte[] BYTES = TEXT.getBytes();

    private static final int CAPACITY = 16;
    private static final int LIMIT = CAPACITY / 4 * 3; // 12
    private ByteBuffer buffer;

    @BeforeEach
    public void init() {
        buffer = ByteBuffer.allocate(CAPACITY); // 16 elements
        buffer.limit(LIMIT); // 12 elements
    }

    @Test
    public void testJDK11Wrap() {
        buffer = ByteBuffer.wrap(BYTES);

        Assertions.assertEquals(BYTES.length, buffer.capacity());
        Assertions.assertEquals(BYTES.length, buffer.limit());
        Assertions.assertEquals(0, buffer.position());
    }

    @Test
    public void testPut() {
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        Assertions.assertEquals(2, buffer.position());

        buffer.put(5, (byte) 5);
        Assertions.assertEquals(2, buffer.position()); // NOTE: position not changed

        buffer.clear();
        buffer.put(TEXT.getBytes(), 2, 3);
        Assertions.assertEquals(3, buffer.position());
        buffer.flip();
        ByteBuffer buffer1 = ByteBuffer.allocate(5);
        buffer1.put(buffer);
    }

    @Test
    public void testPutException() {
        Assertions.assertThrows(BufferOverflowException.class, () -> {
            buffer.put(BYTES).put(BYTES);  // 20 elements are being added
        });
    }

    @Test
    public void testGet() {
        buffer.put(BYTES); // 10 elements added

        // One byte
        buffer.rewind();
        byte b = buffer.get();
        Assertions.assertEquals(BYTES[0], b);
        b = buffer.get(3);
        Assertions.assertEquals(BYTES[3], b);
        Assertions.assertEquals(1, buffer.position());

        // bytes
        byte[] dest = new byte[5];
        buffer.get(dest);
        Assertions.assertEquals(6, buffer.position());
        Assertions.assertEquals("23456", new String(dest));

        // sub-bytes
        Arrays.fill(dest, BYTES[0]);
        buffer.rewind();
        buffer.position(5);
        buffer.get(dest, 1, 3);
        Assertions.assertEquals(8, buffer.position());
        Assertions.assertEquals("16781", new String(dest));
    }

    @Test
    public void testGetException() {
        buffer.put(BYTES);
        buffer.rewind();

        byte[] dest = new byte[100];
        Assertions.assertThrows(BufferUnderflowException.class, () -> {
            buffer.get(dest);
        });
    }

    @Test
    public void testJDK11Mismatch() {
        buffer = ByteBuffer.wrap(BYTES);
        Assertions.assertEquals(-1, buffer.mismatch(ByteBuffer.wrap(BYTES)));

        buffer = ByteBuffer.wrap(BYTES, 1, BYTES.length - 1);
        Assertions.assertEquals(0, buffer.mismatch(ByteBuffer.wrap(BYTES)));

        buffer = ByteBuffer.wrap(BYTES, 0, BYTES.length / 2);
        Assertions.assertEquals(BYTES.length / 2, buffer.mismatch(ByteBuffer.wrap(BYTES)));
    }
}
