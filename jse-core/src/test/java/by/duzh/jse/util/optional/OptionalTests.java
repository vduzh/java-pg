package by.duzh.jse.util.optional;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalTests {

    @Test(expected = NullPointerException.class)
    public void testOf() {
        Optional<String> optional = Optional.of("foo");

        optional = Optional.of(null);
    }

    @Test
    public void testOfNullable() {
        Optional<String> optional = Optional.ofNullable("foo");
        Assert.assertTrue(optional.isPresent());

        optional = Optional.ofNullable(null);
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(Optional.of("foo").isPresent());
        Assert.assertFalse(Optional.ofNullable(null).isPresent());
        Assert.assertFalse(Optional.empty().isPresent());
    }

    @Test
    public void testJDK11IsEmpty() {
        Assert.assertTrue(Optional.ofNullable(null).isEmpty());
        Assert.assertTrue(Optional.empty().isEmpty());
        Assert.assertFalse(Optional.of("foo").isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGet() {
        Assert.assertEquals("foo", Optional.of("foo").get());

        Optional.empty().get();
    }

    @Test
    public void testEmpty() {
        Optional<String> optional = Optional.ofNullable(null);

        Assert.assertFalse(optional.isPresent());
        Assert.assertTrue(optional.equals(Optional.empty()));
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(Optional.of("a"), Optional.of("a"));
        Assert.assertNotEquals(Optional.of("a"), Optional.of("b"));
    }

    @Test
    public void testFilter() {
        Optional<Integer> optional = Optional.of(123).filter(n -> n == 123);
        Assert.assertTrue(optional.isPresent() && 123 == optional.get());

        optional = Optional.of(123).filter(n -> n != 123);
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void testIfPresent() {
        StringBuilder sb = new StringBuilder();

        Optional<String> optional = Optional.of("foo");
        optional.ifPresent(s -> sb.append("Ok"));
        Assert.assertEquals("Ok", sb.toString());

        StringBuilder sb2 = new StringBuilder();

        optional = Optional.empty();
        optional.ifPresent(s -> sb2.append("Ok"));
        Assert.assertEquals(0, sb2.length());
    }

    @Test
    public void testMap() {
        Optional<String> optional = Optional.of("foo").map(s -> "bar");
        Assert.assertEquals("bar", optional.get());

        optional = Optional.empty().map(s -> "bar");
        Assert.assertFalse(optional.isPresent());
    }


    @Test
    public void testFlatMap() {
        Optional<String> optional = Optional.of("foo").flatMap(s -> Optional.of("bar"));
        Assert.assertEquals("bar", optional.get());

        optional = Optional.empty().flatMap(s -> Optional.of("bar"));
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void testOrElse() {
        String s = Optional.of("test").orElse("foo");
        Assert.assertEquals(s, "test");

        s = Optional.<String>empty().orElse("foo");
        Assert.assertEquals(s, "foo");
    }

    @Test
    public void testOrElseGet() {
        Assert.assertEquals(Optional.of("test").orElseGet(() -> "foo"), "test");
        Assert.assertEquals(Optional.empty().orElseGet(() -> "foo"), "foo");
    }

    @Test(expected = RuntimeException.class)
    public void testOrElseThrowNoException() throws Exception {

        String s = Optional.of("test").orElseThrow(Exception::new);
        Assert.assertEquals(s, "test");

        s = Optional.<String>empty().orElseThrow(RuntimeException::new);
    }
}
