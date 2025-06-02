package by.duzh.jse.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferTest {
    private static final String TEXT = "1234567890"; // length = 10

    private static final int CAPACITY = 16;
    private static final int LIMIT = CAPACITY / 4 * 3; // 12
    private Buffer buffer;

    @BeforeEach
    public void init() {
        ByteBuffer b = ByteBuffer.allocate(CAPACITY); // 16 elements
        b.limit(LIMIT); // 12 elements
        buffer = b.put(TEXT.getBytes()); // 10 elements added
    }

    @Test
    public void testIsReadOnly() {
        //TODO: where to set???
        Assertions.assertFalse(buffer.isReadOnly());
    }

    @Test
    public void testPosition() {
        Assertions.assertEquals(TEXT.length(), buffer.position()); //10
    }

    @Test
    public void testSetPosition() {
        buffer.position(5);

        Assertions.assertEquals(5, buffer.position());
    }

    @Test
    public void testCapacity() {
        Assertions.assertEquals(CAPACITY, buffer.capacity());
    }

    @Test
    public void testGetLimit() {
        Assertions.assertEquals(LIMIT, buffer.limit());
    }

    @Test
    public void tesSetLimit() {
        Buffer b = buffer.limit(LIMIT + 2);

        Assertions.assertTrue(b == buffer);
        Assertions.assertEquals(CAPACITY, buffer.capacity());
        Assertions.assertEquals(LIMIT + 2, buffer.limit());
    }

    @Test
    public void testClear() {
        Assertions.assertEquals(LIMIT, buffer.limit());

        Buffer b = buffer.clear();

        Assertions.assertTrue(b == buffer);
        Assertions.assertEquals(CAPACITY, buffer.capacity());
        Assertions.assertEquals(CAPACITY, buffer.limit()); // LIMIT Capacity now!
        Assertions.assertEquals(0, buffer.position());
    }

    @Test
    public void testHasRemaining() {
        Assertions.assertTrue(buffer.hasRemaining());
    }

    @Test
    public void testRemaining() {
        Assertions.assertEquals(buffer.limit() - buffer.position(), buffer.remaining()); // 4

        buffer.clear();
        Assertions.assertEquals(buffer.limit(), buffer.remaining()); // 16

        buffer.limit(LIMIT);
        Assertions.assertEquals(buffer.limit(), buffer.remaining()); // 12
    }

    @Test
    public void testRewind() {
        buffer.position(5);

        buffer.rewind();

        Assertions.assertEquals(0, buffer.position());
    }

    @Test
    public void testFlip() {
        Assertions.assertEquals(TEXT.length(), buffer.position()); // 10
        Assertions.assertEquals(LIMIT, buffer.limit()); // 12

        int oldPosition = buffer.position();

        buffer.flip();

        Assertions.assertEquals(0, buffer.position());
        Assertions.assertEquals(oldPosition, buffer.limit()); // 10
    }

    @Test
    public void testMark() {
        buffer.position(5);
        buffer.mark();
    }

    @Test
    public void testReset() {
        buffer.position(5);
        buffer.mark();
        buffer.position(7); // Position MUST BE more then Mark!!!

        buffer.reset();

        Assertions.assertEquals(5, buffer.position());
    }

    @Test
    public void testHasArray() {
        Assertions.assertTrue(buffer.hasArray());
    }

    @Test
    public void testArray() {
        Object obj = buffer.array();
        Assertions.assertTrue(obj instanceof byte[]);
    }

    @Test
    public void testArrayOffset() {
        //TODO: not clear!!!
        int offset = buffer.arrayOffset();
        Assertions.assertEquals(0, offset);
    }

    @Test
    public void testIsDirect() {
        //TODO: Where to set
        Assertions.assertFalse(buffer.isDirect());
    }

    @Test
    public void testJDK9Duplicate() {
        Buffer duplicate = buffer.duplicate();

        Assertions.assertEquals(16, duplicate.capacity());
        Assertions.assertEquals(12, duplicate.limit());
        Assertions.assertEquals(10, duplicate.position());
   }

    @Test
    public void testJDK9Slice() {
        buffer.position(5);
        Buffer slice = buffer.slice();

        Assertions.assertEquals(7, slice.capacity()); // buffer.limit - 5
        Assertions.assertEquals(7, slice.limit()); // buffer.limit - 5
        Assertions.assertEquals(0, slice.position());
    }
}