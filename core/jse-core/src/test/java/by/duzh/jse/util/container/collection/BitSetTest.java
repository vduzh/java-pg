package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class BitSetTest {
    private BitSet bitSet;

    @BeforeEach
    public void init() {
        bitSet = new BitSet();
    }

    @Test
    public void testCreate() {
        bitSet = new BitSet();
    }

    @Test
    public void testAnd() {
        bitSet.set(1, 3);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(2);

        bitSet.and(bitSet2);

        Assertions.assertEquals(1, bitSet.cardinality());
        Assertions.assertFalse(bitSet.get(0));
        Assertions.assertFalse(bitSet.get(1));
        Assertions.assertTrue(bitSet.get(2));
        Assertions.assertFalse(bitSet.get(3));
    }

    @Test
    public void testAndNot() {
        bitSet.set(1, 4);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(2);

        bitSet.andNot(bitSet2);

        Assertions.assertEquals(2, bitSet.cardinality());
        Assertions.assertFalse(bitSet.get(0));
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
        Assertions.assertTrue(bitSet.get(3));
    }

    @Test
    public void testCardinality() {
        Assertions.assertEquals(0, bitSet.cardinality());

        bitSet.set(1);
        Assertions.assertEquals(1, bitSet.cardinality());
    }

    @Test
    public void testClear() {
        bitSet.set(1, 4);

        bitSet.clear();
        Assertions.assertEquals(0, bitSet.cardinality());

        bitSet.set(1, 4);
        bitSet.clear(2);

        Assertions.assertEquals(2, bitSet.cardinality());
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
        Assertions.assertTrue(bitSet.get(3));

        bitSet.set(1, 5);
        bitSet.clear(2, 4);

        Assertions.assertEquals(2, bitSet.cardinality());
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
        Assertions.assertFalse(bitSet.get(3));
        Assertions.assertTrue(bitSet.get(4));
    }

    @Test
    public void testClone() {
        bitSet.set(1, 3);

        BitSet clone = (BitSet) bitSet.clone();

        Assertions.assertEquals(2, clone.cardinality());
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertTrue(bitSet.get(2));
    }

    @Test
    public void testEquals() {
        bitSet.set(1, 3);

        BitSet bitSet2 = new BitSet();

        bitSet2.set(1, 3);
        Assertions.assertTrue(bitSet.equals(bitSet2));

        bitSet2.set(1, 4);
        Assertions.assertFalse(bitSet.equals(bitSet2));
    }

    @Test
    public void testFlip() {
        bitSet.set(1, 4);
        bitSet.flip(1);

        Assertions.assertEquals(2, bitSet.cardinality());
        Assertions.assertTrue(bitSet.get(2));
        Assertions.assertTrue(bitSet.get(3));

        bitSet.set(1, 4);
        bitSet.flip(3, 5);

        Assertions.assertEquals(3, bitSet.cardinality());
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertTrue(bitSet.get(2));
        Assertions.assertFalse(bitSet.get(3));
        Assertions.assertTrue(bitSet.get(4));
    }

    @Test
    public void testGet() {
        bitSet = new BitSet();
        bitSet.set(1);

        Assertions.assertFalse(bitSet.get(0));
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
    }

    @Test
    public void testGetBitSet() {
        bitSet = new BitSet();
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(5);

        bitSet = bitSet.get(2, 4);

        Assertions.assertFalse(bitSet.get(0));
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
    }

    @Test
    public void testIntersects() {
        bitSet = new BitSet();
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(5);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(3);

        Assertions.assertTrue(bitSet.intersects(bitSet2));

        bitSet2.clear(3);
        Assertions.assertFalse(bitSet.intersects(bitSet2));
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(bitSet.isEmpty());
    }

    @Test
    public void testLength() {
        bitSet.set(2, 4);

        Assertions.assertEquals(4, bitSet.length());
        Assertions.assertEquals(2, bitSet.cardinality());
    }

    @Test
    public void testPreviousClearBit() {
        bitSet.set(0, 3);
        bitSet.set(4);

        Assertions.assertEquals(3, bitSet.previousClearBit(4));
        Assertions.assertEquals(-1, bitSet.previousClearBit(1));
    }

    @Test
    public void testPreviousSetBit() {
        bitSet.set(2, 3);
        bitSet.set(4);

        Assertions.assertEquals(4, bitSet.previousSetBit(5));
        Assertions.assertEquals(-1, bitSet.previousSetBit(1));
    }

    @Test
    public void testSet() {
        bitSet.set(1);
        Assertions.assertTrue(bitSet.get(1));

        bitSet.set(1, false);
        Assertions.assertFalse(bitSet.get(1));

        bitSet.set(1, 3);
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertTrue(bitSet.get(2));
        Assertions.assertFalse(bitSet.get(3));
    }

    @Test
    public void testSize() {
        bitSet.set(1, 3);
        Assertions.assertEquals(64, bitSet.size());

        bitSet.set(100);
        Assertions.assertEquals(128, bitSet.size());
    }

    @Test
    public void testStream() {
        bitSet.set(1, 3);
        IntStream stream = bitSet.stream();
        Assertions.assertEquals(2, stream.count());
    }

    @Test
    public void testToByteArray() {
        bitSet.set(1, 3);
        byte[] bytes = bitSet.toByteArray();
        Assertions.assertEquals(1, bytes.length);
        Assertions.assertEquals(6, bytes[0]); // 0110 в двоичной системе
    }

    @Test
    public void testToLongArray() {
        bitSet.set(1, 3);
        long[] longs = bitSet.toLongArray();
        Assertions.assertEquals(1, longs.length);
        Assertions.assertEquals(6L, longs[0]); // 0110 в двоичной системе
    }

    @Test
    // TODO: analyze!!!
    public void testToString() {
        bitSet.set(1, 3);
        String s = bitSet.toString();
        Assertions.assertEquals("{1, 2}", s);
    }

    @Test
    public void testValueOf() {
        byte[] bytes = new byte[] { 0b0110 }; // 0110 в двоичной системе
        BitSet result = BitSet.valueOf(bytes);
        Assertions.assertEquals(2, result.cardinality());
        Assertions.assertTrue(result.get(1));
        Assertions.assertTrue(result.get(2));
        Assertions.assertFalse(result.get(0));
        Assertions.assertFalse(result.get(3));
    }

    @Test
    public void testXor() {
        bitSet.set(1, 3);

        BitSet bitSet2 = new BitSet();
        bitSet2.set(2, 4);

        bitSet.xor(bitSet2);

        Assertions.assertEquals(2, bitSet.cardinality());
        Assertions.assertTrue(bitSet.get(1));
        Assertions.assertFalse(bitSet.get(2));
        Assertions.assertTrue(bitSet.get(3));
    }
}