package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class BitSetTest {
    private BitSet bitSet;

    @Before
    public void init() {
        bitSet = new BitSet();
    }

    @Test
    public void testCreate() {
        bitSet = new BitSet();
    }

    @Test
    public void testAnd() {
        bitSet.set(1, 2);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(2);

        bitSet.and(bitSet2);

        Assert.assertEquals(1, bitSet.cardinality());
        Assert.assertFalse(bitSet.get(0));
        Assert.assertFalse(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
        Assert.assertFalse(bitSet.get(3));
    }

    @Test
    public void testAndNot() {
        bitSet.set(1, 4);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(2);

        bitSet.andNot(bitSet2);

        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertFalse(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
        Assert.assertTrue(bitSet.get(3));
    }

    @Test
    public void testCardinality() {
        Assert.assertEquals(0, bitSet.cardinality());

        bitSet.set(1);
        Assert.assertEquals(1, bitSet.cardinality());
    }

    @Test
    public void testClear() {
        bitSet.set(1, 4);

        bitSet.clear();
        Assert.assertEquals(0, bitSet.cardinality());

        bitSet.set(1, 4);
        bitSet.clear(2);

        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
        Assert.assertTrue(bitSet.get(3));

        bitSet.set(1, 5);
        bitSet.clear(2, 4);

        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
        Assert.assertFalse(bitSet.get(3));
        Assert.assertTrue(bitSet.get(4));
    }

    @Test
    public void testClone() {
        bitSet.set(1, 3);

        BitSet clone = (BitSet) bitSet.clone();

        Assert.assertEquals(2, clone.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
    }

    @Test
    public void testEquals() {
        bitSet.set(1, 3);

        BitSet bitSet2 = new BitSet();

        bitSet2.set(1, 3);
        Assert.assertTrue(bitSet.equals(bitSet2));

        bitSet2.set(1, 4);
        Assert.assertFalse(bitSet.equals(bitSet2));
    }

    @Test
    public void testFlip() {
        bitSet.set(1, 4);
        bitSet.flip(1);

        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(2));
        Assert.assertTrue(bitSet.get(3));

        bitSet.set(1, 4);
        bitSet.flip(3, 5);

        Assert.assertEquals(3, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(2));
        Assert.assertFalse(bitSet.get(3));
        Assert.assertTrue(bitSet.get(4));
    }

    @Test
    public void testGet() {
        bitSet = new BitSet();
        bitSet.set(1);

        Assert.assertFalse(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
    }

    @Test
    public void testGetBitSet() {
        bitSet = new BitSet();
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(5);

        bitSet = bitSet.get(2, 4);

        Assert.assertFalse(bitSet.get(0));
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
    }

    @Test
    public void testIntersects() {
        bitSet = new BitSet();
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(5);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(3);

        Assert.assertTrue(bitSet.intersects(bitSet2));

        bitSet2.clear(3);
        Assert.assertFalse(bitSet.intersects(bitSet2));
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(bitSet.isEmpty());
    }

    @Test
    public void testLength() {
        bitSet.set(2, 4);

        Assert.assertEquals(4, bitSet.length());
        Assert.assertEquals(2, bitSet.cardinality());
    }

    @Test
    public void testPreviousClearBit() {
        bitSet.set(0, 3);
        bitSet.set(4);

        Assert.assertEquals(3, bitSet.previousClearBit(4));
        Assert.assertEquals(-1, bitSet.previousClearBit(1));
    }

    @Test
    public void testPreviousSetBit() {
        bitSet.set(2, 3);
        bitSet.set(4);

        Assert.assertEquals(4, bitSet.previousSetBit(5));
        Assert.assertEquals(-1, bitSet.previousSetBit(1));
    }

    @Test
    public void testSet() {
        bitSet.set(1);
        Assert.assertEquals(1, bitSet.cardinality());

        bitSet.set(1, false);
        Assert.assertEquals(0, bitSet.cardinality());

        bitSet.set(1, 4);
        Assert.assertEquals(3, bitSet.cardinality());

        bitSet.set(2, 4, false);
        Assert.assertEquals(1, bitSet.cardinality());
    }

    @Test
    public void testSize() {
        bitSet = new BitSet(16);
        Assert.assertEquals(64, bitSet.size());

        bitSet = new BitSet(128);
        Assert.assertEquals(128, bitSet.size());
    }

    @Test
    public void testStream() {
        bitSet.set(1, 3);

        long count = bitSet.stream().count();

        Assert.assertEquals(2, count);
    }

    @Test
    // TODO: analyze!!!
    public void testToByteArray() {
        bitSet.set(1, 3);

        byte[] bytes = bitSet.toByteArray();

        //Assert.assertEquals(2, bytes.length);
    }

    @Test
    // TODO: analyze!!!
    public void testToLongArray() {
        bitSet.set(1, 3);

        long[] bytes = bitSet.toLongArray();

        //Assert.assertEquals(2, bytes.length);
    }

    @Test
    // TODO: analyze!!!
    public void testToString() {
        bitSet.set(1, 3);

        System.out.println(bitSet.toString());
    }

    @Test
    // TODO: analyze!!!
    public void testValueOf() {
    }

    @Test
    public void testXor() {
        bitSet.set(1, 4);

        BitSet bitSet1 = new BitSet();
        bitSet1.set(2);

        bitSet.xor(bitSet1);

        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertFalse(bitSet.get(2));
        Assert.assertTrue(bitSet.get(3));
    }
}