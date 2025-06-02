package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.*;

public class FunctionTest {
    @Test
    public void testFunction() {
        Function<String, Integer> function = s -> s.length() + 1;
        Assertions.assertEquals(4, function.apply("foo").intValue());
    }

    @Test
    public void testIntFunction() {
        IntFunction<String> function = i -> "foo" + i;
        Assertions.assertEquals("foo1", function.apply(1));
    }

    @Test
    public void testLongFunction() {
        LongFunction<String> function = l -> "foo" + l;
        Assertions.assertEquals("foo1", function.apply(1));
    }

    @Test
    public void testDoubleFunction() {
        DoubleFunction<String> function = d -> "foo" + d;
        Assertions.assertEquals("foo1.2", function.apply(1.2));
    }

    @Test
    public void testToIntFunction() {
        ToIntFunction<String> function = String::length;
        Assertions.assertEquals(3, function.applyAsInt("foo"));
    }

    @Test
    public void testToLongFunction() {
        ToLongFunction<String> function = String::length;
        Assertions.assertEquals(3, function.applyAsLong("foo"));
    }

    @Test
    public void testToDoubleFunction() {
        ToDoubleFunction<String> function = String::length;
        Assertions.assertEquals(3.0, function.applyAsDouble("foo"), 0.0);
    }

    @Test
    public void testIntToDoubleFunction() {
        IntToDoubleFunction function = i -> i * 1.1;
        Assertions.assertEquals(3.3, function.applyAsDouble(3), 0.003);
    }

    @Test
    public void testIntToLongFunction() {
        IntToLongFunction function = i -> i * 2;
        Assertions.assertEquals(6, function.applyAsLong(3));
    }

    @Test
    public void testLongToIntFunction() {
        LongToIntFunction function = l -> String.valueOf(l).length();
        Assertions.assertEquals(2, function.applyAsInt(33));
    }

    @Test
    public void testLongToDoubleFunction() {
        IntToDoubleFunction function = l -> l * 1.1;
        Assertions.assertEquals(3.3, function.applyAsDouble(3), 0.003);
    }

    @Test
    public void testBiFunction() {
        BiFunction<String, Long, Integer> function = (s, l) -> s.length() + l.toString().length();
        Assertions.assertEquals(4, function.apply("foo", 1l).intValue());
    }

}
