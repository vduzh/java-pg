package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

public class UnaryOperatorTest {
    @Test
    public void testUnaryOperator() {
        UnaryOperator<Integer> unaryOperator = Math::abs;
        Assertions.assertEquals(4, unaryOperator.apply(-4).intValue());
    }

    @Test
    public void testIntUnaryOperator() {
        IntUnaryOperator unaryOperator = Math::abs;
        Assertions.assertEquals(4, unaryOperator.applyAsInt(-4));
    }

    @Test
    public void testLongUnaryOperator() {
        LongUnaryOperator unaryOperator = Math::abs;
        Assertions.assertEquals(4, unaryOperator.applyAsLong(-4));
    }

    @Test
    public void testDoubleUnaryOperator() {
        DoubleUnaryOperator unaryOperator = Math::abs;
        Assertions.assertEquals(4.0, unaryOperator.applyAsDouble(-4.0), 0.01);
    }
}
