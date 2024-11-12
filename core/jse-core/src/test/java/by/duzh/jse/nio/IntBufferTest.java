package by.duzh.jse.nio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class IntBufferTest {
    private static final int[] INTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    private static final int CAPACITY = 16;
    private static final int LIMIT = CAPACITY / 4 * 3; // 12
    private IntBuffer buffer;

    @Before
    public void init() {
        buffer = IntBuffer.allocate(CAPACITY); // 16 elements
        buffer.limit(LIMIT); // 12 elements
    }

    @Test
    public void testWrap() throws Exception {
        throw new Exception("testWrap needs to be tested!");
    }

    @Test
    public void testPut() {
        buffer.put(1);
        buffer.put(2);
        Assert.assertEquals(2, buffer.position());

        buffer.put(5, (byte) 5);
        Assert.assertEquals(2, buffer.position()); // NOTE: position not changed

        buffer.clear();
        buffer.put(INTS, 2, 3);
        Assert.assertEquals(3, buffer.position());
        buffer.flip();
        IntBuffer buffer1 = IntBuffer.allocate(5);
        buffer1.put(buffer);
    }

    @Test(expected = BufferOverflowException.class)
    public void testPutException() {
        buffer.put(INTS).put(INTS);  // 20 elements are being added
    }

    @Test
    public void testGet() {
        buffer.put(INTS); // 10 elements added

        // One byte
        buffer.rewind();
        int i = buffer.get();
        Assert.assertEquals(INTS[0], i);
        i = buffer.get(3);
        Assert.assertEquals(INTS[3], i);
        Assert.assertEquals(1, buffer.position());

        // bytes
        int[] dest = new int[5];
        buffer.get(dest);
        Assert.assertEquals(6, buffer.position());
        //TODO: use stream to join ints
        // Assert.assertEquals("23456",
        // sub-bytes
        Arrays.fill(dest, INTS[0]);
        buffer.rewind();
        buffer.position(5);
        buffer.get(dest, 1, 3);
        Assert.assertEquals(8, buffer.position());
        //TODO: use stream to join ints
        //Assert.assertEquals(Arrays.equals(dest)  );
        //16781"
    }

    @Test(expected = BufferUnderflowException.class)
    public void testGetException() {
        buffer.put(INTS);
        buffer.rewind();

        int[] dest = new int[100];
        buffer.get(dest);
    }

    @Test
    public void testJDK11Mismatch() {
        buffer = IntBuffer.wrap(INTS);
        Assert.assertEquals(-1, buffer.mismatch(IntBuffer.wrap(INTS)));

        buffer = IntBuffer.wrap(INTS, 1, INTS.length - 1);
        Assert.assertEquals(0, buffer.mismatch(IntBuffer.wrap(INTS)));

        buffer = IntBuffer.wrap(INTS, 0, INTS.length / 2);
        Assert.assertEquals(INTS.length / 2, buffer.mismatch(IntBuffer.wrap(INTS)));
    }
}
