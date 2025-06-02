package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.*;

public class PredicateTest {
    @Test
    public void testPredicate() {
        Predicate<String> predicate = s -> s.length() == 3;

        Assertions.assertTrue(predicate.test("foo"));
        Assertions.assertFalse(predicate.test("foo1"));
    }

    @Test
    public void testJDK11PredicateNot() {
        Predicate<String> predicate = Predicate.not(String::isBlank);

        Assertions.assertTrue(predicate.test("foo"));
        Assertions.assertFalse(predicate.test("  "));
    }

    @Test
    public void testIntPredicate() {
        IntPredicate predicate = i -> i > 0;
        Assertions.assertTrue(predicate.test(3));
    }

    @Test
    public void testLongPredicate() {
        LongPredicate predicate = l -> l > 0;
        Assertions.assertTrue(predicate.test(3l));
    }

    @Test
    public void testDoublePredicate() {
        DoublePredicate predicate = d -> d > 0;
        Assertions.assertTrue(predicate.test(3.1));
    }

    @Test
    public void testBiPredicate() {
        BiPredicate<String, Integer> predicate = (s, i) -> s.length() == i;
        Assertions.assertTrue(predicate.test("foo", 3));
        Assertions.assertFalse(predicate.test("foo1", 3));
    }
}
