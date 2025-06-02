package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

public class BinaryOperatorTest {
    @Test
    public void testBinaryOperator() {
        BinaryOperator<Integer> binaryOperator = Integer::sum;
        Assertions.assertEquals(4, binaryOperator.apply(1, 3).intValue());
    }

    @Test
    public void testIntBinaryOperator() {
        IntBinaryOperator binaryOperator = Integer::sum;
        Assertions.assertEquals(4, binaryOperator.applyAsInt(3, 1));
    }

    @Test
    public void testLongBinaryOperator() {
        LongBinaryOperator binaryOperator = Long::sum;
        Assertions.assertEquals(4, binaryOperator.applyAsLong(3, 1));
    }

    @Test
    public void testDoubleBinaryOperator() {
        DoubleBinaryOperator binaryOperator = Double::sum;
        Assertions.assertEquals(4, binaryOperator.applyAsDouble(2.7, 1.3), 0.1);
    }

    @Test
    public void testMaxBy() {
        BinaryOperator<String> maxBy = BinaryOperator.maxBy(String::compareTo);
        String max = maxBy.apply("bcd", "abc");

        Assertions.assertEquals("bcd", max);
    }

    @Test
    public void testMinBy() {
        BinaryOperator<String> minBy = BinaryOperator.minBy(String::compareTo);
        String min = minBy.apply("bcd", "abc");

        Assertions.assertEquals("abc", min);
    }

    @Test
    public void testMaxByWithNumbers() {
        BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(Integer::compareTo);
        Integer max = maxBy.apply(15, 6);

        Assertions.assertEquals(15, max.intValue());
    }

    @Test
    public void testMinByWithNumbers() {
        BinaryOperator<Integer> minBy = BinaryOperator.minBy(Integer::compareTo);
        Integer min = minBy.apply(15, 6);

        Assertions.assertEquals(6, min.intValue());
    }
}
