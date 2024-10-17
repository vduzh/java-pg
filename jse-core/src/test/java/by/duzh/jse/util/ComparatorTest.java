package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testReversed() {
        Comparator<Integer> comparator = new SimpleComparator().reversed();

        Assert.assertTrue(comparator.compare(10, 3) < 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) > 0);
    }

    @Test
    public void testReverseNaturalOrderOfElements() {
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Assert.assertTrue(comparator.compare(10, 3) < 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) > 0);
    }

    @Test
    public void testNaturalOrderOfElements() {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testNullsFirst() {
        Comparator<Integer> comparator = Comparator.nullsFirst(new SimpleComparator());

        Assert.assertTrue(comparator.compare(10, null) > 0);
        Assert.assertEquals(0, comparator.compare(null, null));
        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testNullsLast() {
        Comparator<Integer> comparator = Comparator.nullsLast(new SimpleComparator());

        Assert.assertTrue(comparator.compare(10, null) < 0);
        Assert.assertEquals(0, comparator.compare(null, null));
        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithComparator() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing((a, b) -> -1);

        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(1, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractor() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing(a -> a * 100);

        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparator() {
        Comparator<Integer> comparator = new SimpleComparator().thenComparing(
                a -> String.valueOf(a * 100),
                (s1, s2) -> s1.length() - s2.length()
        );

        Assert.assertTrue(comparator.compare(10, 3) > 0);
        Assert.assertEquals(0, comparator.compare(10, 10));
        Assert.assertTrue(comparator.compare(3, 10) < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparatorSample() {
        Comparator<String> comparator = Comparator.<String>comparingInt(s -> s.length())
                .thenComparing((s1, s2) -> s1.compareToIgnoreCase(s2));

        Assert.assertTrue(comparator.compare("Bb", "aA") > 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aA", "Bb") < 0);
    }

    @Test
    public void testThenComparingWithKeyExtractorAndKeyComparatorSample2() {
        Comparator<String> comparator = Comparator.comparingInt(String::length)
                .thenComparing(String::compareToIgnoreCase);

        Assert.assertTrue(comparator.compare("Bb", "aA") > 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aA", "Bb") < 0);
    }

    @Test
    public void testComparingDouble() {
        Comparator<String> comparator = Comparator.comparingDouble(String::length);

        Assert.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingInt() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Assert.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingLong() {
        Comparator<String> comparator = Comparator.comparingLong(String::length);

        Assert.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingWithKey() {
        Comparator<String> comparator = Comparator.comparing(String::length);

        Assert.assertTrue(comparator.compare("Bb", "aaaa") < 0);
        Assert.assertEquals(0, comparator.compare("a", "A"));
        Assert.assertTrue(comparator.compare("aAaaa", "Bb") > 0);
    }

    @Test
    public void testComparingWithKeyAndKeyComparator() {
        Comparator<String> comparator = Comparator.comparing(String::toUpperCase,
                String::compareTo);

        Assert.assertTrue(comparator.compare("Bb", "aaaa") > 0);
        Assert.assertEquals(0, comparator.compare("aaBB", "AAbb"));
        Assert.assertTrue(comparator.compare("aAaaa", "Bb") < 0);
    }
}
