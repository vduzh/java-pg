package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;

public class ObjectsTests {

    @Test
    public void testCompare() {
        Assertions.assertTrue(Objects.compare("a", "aaa", (a, b) -> a.length() - b.length()) != 0);
        Assertions.assertEquals(0, Objects.compare("a", "a", Comparator.comparingInt(String::length)));
    }

    @Test
    public void testDeepEquals() {
        Assertions.assertTrue(Objects.deepEquals("a", "a"));
        Assertions.assertFalse(Objects.deepEquals("a", "b"));

        String[] a = {"a", "b"};
        String[] b = {"a", "b"};
        Assertions.assertTrue(Objects.deepEquals(a, b));

        b[1] = "c";
        Assertions.assertFalse(Objects.deepEquals(a, b));
    }

    @Test
    public void testEquals() {
        Assertions.assertTrue(Objects.equals("a", "a"));
        Assertions.assertFalse(Objects.equals("a", "b"));
    }

    @Test
    public void testIsNull() {
        String s = null;
        Assertions.assertTrue(Objects.isNull(s));
        Assertions.assertFalse(Objects.isNull("a"));
    }

    @Test
    public void testNonNull() {
        String s = null;
        Assertions.assertTrue(Objects.nonNull("a"));
        Assertions.assertFalse(Objects.nonNull(s));
    }

    @Test
    public void testRequireNonNull() {
        String s = null;
        Objects.requireNonNull("a");
        Assertions.assertThrows(NullPointerException.class, () -> Objects.requireNonNull(s));
    }

    @Test
    public void testJDK9RequireNonNullElse() throws Exception {
        Assertions.assertEquals("foo", Objects.requireNonNullElse("foo", "bar"));
        Assertions.assertEquals("bar", Objects.requireNonNullElse(null, "bar"));
    }

    @Test
    public void testJDK9RRequireNonNullElseGet() throws Exception {
        Assertions.assertEquals("foo", Objects.requireNonNullElseGet("foo", () -> "bar"));
        Assertions.assertEquals("bar", Objects.requireNonNullElseGet(null, () -> "bar"));
    }

    @Test
    public void testJDK9RCheckIndex() throws Exception {
        // OK
        Assertions.assertEquals(3, Objects.checkIndex(3, 7));
        // Exception
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkIndex(7, 5));
    }

    @Test
    public void testJDK9RCheckFromIndexSize() throws Exception {
        // OK
        Assertions.assertEquals(3, Objects.checkFromIndexSize(3, 4, 10));
        // Exception
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkFromIndexSize(3, 4, 5));
    }

    @Test
    public void testJDK9RCheckFromToIndex() throws Exception {
        // OK
        Assertions.assertEquals(3, Objects.checkFromToIndex(3, 7, 10));
        // Exception
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkFromToIndex(3, 7, 5));
    }
}
