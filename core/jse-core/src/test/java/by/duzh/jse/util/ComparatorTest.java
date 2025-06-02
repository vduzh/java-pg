package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

//TODO: have a look again
public class ComparatorTest {
    static class SimpleComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    @Test
    public void testCreate() {
        Comparator<Integer> comparator = new SimpleComparator();

        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testReversed() {
        Comparator<Integer> comparator = new SimpleComparator().reversed();

        Assertions.assertTrue(comparator.compare(10, 3) < 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) > 0);
    }

    @Test
    public void testReverseNaturalOrderOfElements() {
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Assertions.assertTrue(comparator.compare(10, 3) < 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) > 0);
    }

    @Test
    public void testNaturalOrderOfElements() {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testNullsFirst() {
        Comparator<Integer> comparator = Comparator.nullsFirst(new SimpleComparator());

        Assertions.assertTrue(comparator.compare(10, null) > 0);
        Assertions.assertEquals(0, comparator.compare(null, null));
        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testNullsLast() {
        Comparator<Integer> comparator = Comparator.nullsLast(new SimpleComparator());

        Assertions.assertTrue(comparator.compare(10, null) < 0);
        Assertions.assertEquals(0, comparator.compare(null, null));
        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithComparator() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing((a, b) -> -1);

        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(-1, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractor() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing(a -> a * 100);

        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparator() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing(
                a -> String.valueOf(a * 100),
                (s1, s2) -> s1.length() - s2.length()
        );

        Assertions.assertTrue(comparator.compare(10, 3) > 0);
        Assertions.assertEquals(0, comparator.compare(10, 10));
        Assertions.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparatorSample() {
        Comparator<String> comparator = Comparator.<String>comparingInt(s -> s.length())
                .thenComparing((s1, s2) -> s1.compareToIgnoreCase(s2));

        Assertions.assertTrue(comparator.compare("Bb", "aA") > 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aA", "Bb") < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparatorSample2() {
        Comparator<String> comparator = Comparator.comparingInt(String::length)
                .thenComparing(String::compareToIgnoreCase);

        Assertions.assertTrue(comparator.compare("Bb", "aA") > 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aA", "Bb") < 0);
    }

    @Test
    public void testComparingDouble() {
        Comparator<String> comparator = Comparator.comparingDouble(String::length);

        Assertions.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingInt() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Assertions.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingLong() {
        Comparator<String> comparator = Comparator.comparingLong(String::length);

        Assertions.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingWithKey() {
        Comparator<String> comparator = Comparator.comparing(String::length);

        Assertions.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assertions.assertEquals(0, comparator.compare("a", "A"));
        Assertions.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingWithKeyAndKeyComparator() {
        Comparator<String> comparator = Comparator.comparing(String::toUpperCase,
                String::compareTo);

        Assertions.assertTrue(comparator.compare("Bb", "aaaa") > 0);
        Assertions.assertEquals(0, comparator.compare("aaBB", "AAbb"));
        Assertions.assertTrue(comparator.compare("aAaaa", "Bb") < 0);
    }
}
