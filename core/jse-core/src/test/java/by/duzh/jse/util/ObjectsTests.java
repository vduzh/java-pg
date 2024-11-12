package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;

public class ObjectsTests {

    @Test
    public void testCompare() {
        Assert.assertTrue(Objects.compare("a", "aaa", (a, b) -> a.length() - b.length()) != 0);
        Assert.assertEquals(0, Objects.compare("a", "a", Comparator.comparingInt(String::length)));
    }

    @Test
    public void testDeepEquals() {
        Assert.assertTrue(Objects.deepEquals("a", "a"));
        Assert.assertFalse(Objects.deepEquals("a", "b"));

        String[] a = {"a", "b"};
        String[] b = {"a", "b"};
        Assert.assertTrue(Objects.deepEquals(a, b));

        b[1] = "c";
        Assert.assertFalse(Objects.deepEquals(a, b));
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(Objects.equals("a", "a"));
        Assert.assertFalse(Objects.equals("a", "b"));
    }

    @Test
    public void testIsNull() {
        String s = null;
        Assert.assertTrue(Objects.isNull(s));
        Assert.assertFalse(Objects.isNull("a"));
    }

    @Test
    public void testNonNull() {
        String s = null;
        Assert.assertTrue(Objects.nonNull("a"));
        Assert.assertFalse(Objects.nonNull(s));
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNull() {
        String s = null;
        Objects.requireNonNull("a");
        Objects.requireNonNull(s);
    }

    @Test
    public void testJDK9RequireNonNullElse() throws Exception {
        Assert.assertEquals("foo", Objects.requireNonNullElse("foo", "bar"));
        Assert.assertEquals("bar", Objects.requireNonNullElse(null, "bar"));
    }

    @Test
    public void testJDK9RRequireNonNullElseGet() throws Exception {
        Assert.assertEquals("foo", Objects.requireNonNullElseGet("foo", () -> "bar"));
        Assert.assertEquals("bar", Objects.requireNonNullElseGet(null, () -> "bar"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testJDK9RCheckIndex() throws Exception {
        // OK
        Assert.assertEquals(3, Objects.checkIndex(3, 7));
        // Exception
        Objects.checkIndex(7, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testJDK9RCheckFromIndexSize() throws Exception {
        // OK
        Assert.assertEquals(3, Objects.checkFromIndexSize(3, 4, 10));
        // Exception
        Objects.checkFromIndexSize(3, 4, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testJDK9RCheckFromToIndex() throws Exception {
        // OK
        Assert.assertEquals(3, Objects.checkFromToIndex(3, 7, 10));
        // Exception
        Objects.checkFromToIndex(3, 7, 5);
    }
}
