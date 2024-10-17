package by.duzh.jse.nio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferTest {
    private static final String TEXT = "1234567890"; // length = 10

    private static final int CAPACITY = 16;
    private static final int LIMIT = CAPACITY / 4 * 3; // 12
    private Buffer buffer;

    @Before
    public void init() {
        ByteBuffer b = ByteBuffer.allocate(CAPACITY); // 16 elements
        b.limit(LIMIT); // 12 elements
        buffer = b.put(TEXT.getBytes()); // 10 elements added
    }

    @Test
    public void testIsReadOnly() {
        //TODO: where to set???
        Assert.assertFalse(buffer.isReadOnly());
    }

    @Test
    public void testPosition() {
        Assert.assertEquals(TEXT.length(), buffer.position()); //10
    }

    @Test
    public void testSetPosition() {
        buffer.position(5);

        Assert.assertEquals(5, buffer.position());
    }

    @Test
    public void testCapacity() {
        Assert.assertEquals(CAPACITY, buffer.capacity());
    }

    @Test
    public void testGetLimit() {
        Assert.assertEquals(LIMIT, buffer.limit());
    }

    @Test
    public void tesSetLimit() {
        Buffer b = buffer.limit(LIMIT + 2);

        Assert.assertTrue(b == buffer);
        Assert.assertEquals(CAPACITY, buffer.capacity());
        Assert.assertEquals(LIMIT + 2, buffer.limit());
    }

    @Test
    public void testClear() {
        Assert.assertEquals(LIMIT, buffer.limit());

        Buffer b = buffer.clear();

        Assert.assertTrue(b == buffer);
        Assert.assertEquals(CAPACITY, buffer.capacity());
        Assert.assertEquals(CAPACITY, buffer.limit()); // LIMIT Capacity now!
        Assert.assertEquals(0, buffer.position());
    }

    @Test
    public void testHasRemaining() {
        Assert.assertTrue(buffer.hasRemaining());
    }

    @Test
    public void testRemaining() {
        Assert.assertEquals(buffer.limit() - buffer.position(), buffer.remaining()); // 4

        buffer.clear();
        Assert.assertEquals(buffer.limit(), buffer.remaining()); // 16

        buffer.limit(LIMIT);
        Assert.assertEquals(buffer.limit(), buffer.remaining()); // 12
    }

    @Test
    public void testRewind() {
        buffer.position(5);

        buffer.rewind();

        Assert.assertEquals(0, buffer.position());
    }

    @Test
    public void testFlip() {
        Assert.assertEquals(TEXT.length(), buffer.position()); // 10
        Assert.assertEquals(LIMIT, buffer.limit()); // 12

        int oldPosition = buffer.position();

        buffer.flip();

        Assert.assertEquals(0, buffer.position());
        Assert.assertEquals(oldPosition, buffer.limit()); // 10
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

        Assert.assertEquals(5, buffer.position());
    }

    @Test
    public void testHasArray() {
        Assert.assertTrue(buffer.hasArray());
    }

    @Test
    public void testArray() {
        Object obj = buffer.array();
        Assert.assertTrue(obj instanceof byte[]);
    }

    @Test
    public void testArrayOffset() {
        //TODO: not clear!!!
        int offset = buffer.arrayOffset();
        Assert.assertEquals(0, offset);
    }

    @Test
    public void testIsDirect() {
        //TODO: Where to set
        Assert.assertFalse(buffer.isDirect());
    }

    @Test
    public void testJDK9Duplicate() {
        Buffer duplicate = buffer.duplicate();

        Assert.assertEquals(16, duplicate.capacity());
        Assert.assertEquals(12, duplicate.limit());
        Assert.assertEquals(10, duplicate.position());
   }

    @Test
    public void testJDK9Slice() {
        buffer.position(5);
        Buffer slice = buffer.slice();

        Assert.assertEquals(7, slice.capacity()); // buffer.limit - 5
        Assert.assertEquals(7, slice.limit()); // buffer.limit - 5
        Assert.assertEquals(0, slice.position());
    }
}