package by.duzh.jse.util.function;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

public class BinaryOperatorTest {
    @Test
    public void testBinaryOperator() {
        BinaryOperator<Integer> binaryOperator = Integer::sum;
        Assert.assertEquals(4, binaryOperator.apply(1, 3).intValue());
    }

    @Test
    public void testIntBinaryOperator() {
        IntBinaryOperator binaryOperator = Integer::sum;
        Assert.assertEquals(4, binaryOperator.applyAsInt(3, 1));
    }

    @Test
    public void testLongBinaryOperator() {
        LongBinaryOperator binaryOperator = Long::sum;
        Assert.assertEquals(4, binaryOperator.applyAsLong(3, 1));
    }

    @Test
    public void testDoubleBinaryOperator() {
        DoubleBinaryOperator binaryOperator = Double::sum;
        Assert.assertEquals(4, binaryOperator.applyAsDouble(2.7, 1.3), 0.1);
    }
}
