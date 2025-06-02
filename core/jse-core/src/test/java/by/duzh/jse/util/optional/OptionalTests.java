package by.duzh.jse.util.optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalTests {

    @Test
    public void testOf() {
        Optional<String> optional = Optional.of("foo");

        Assertions.assertThrows(NullPointerException.class, () -> {
            Optional.of(null);
        });
    }

    @Test
    public void testOfNullable() {
        Optional<String> optional = Optional.ofNullable("foo");
        Assertions.assertTrue(optional.isPresent());

        optional = Optional.ofNullable(null);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    public void testIsPresent() {
        Assertions.assertTrue(Optional.of("foo").isPresent());
        Assertions.assertFalse(Optional.ofNullable(null).isPresent());
        Assertions.assertFalse(Optional.empty().isPresent());
    }

    @Test
    public void testJDK11IsEmpty() {
        Assertions.assertTrue(Optional.ofNullable(null).isEmpty());
        Assertions.assertTrue(Optional.empty().isEmpty());
        Assertions.assertFalse(Optional.of("foo").isEmpty());
    }

    @Test
    public void testGet() {
        Assertions.assertEquals("foo", Optional.of("foo").get());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Optional.empty().get();
        });
    }

    @Test
    public void testEmpty() {
        Optional<String> optional = Optional.ofNullable(null);

        Assertions.assertFalse(optional.isPresent());
        Assertions.assertTrue(optional.equals(Optional.empty()));
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(Optional.of("a"), Optional.of("a"));
        Assertions.assertNotEquals(Optional.of("a"), Optional.of("b"));
    }

    @Test
    public void testFilter() {
        Optional<Integer> optional = Optional.of(123).filter(n -> n == 123);
        Assertions.assertTrue(optional.isPresent() && 123 == optional.get());

        optional = Optional.of(123).filter(n -> n != 123);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    public void testIfPresent() {
        StringBuilder sb = new StringBuilder();

        Optional<String> optional = Optional.of("foo");
        optional.ifPresent(s -> sb.append("Ok"));
        Assertions.assertEquals("Ok", sb.toString());

        StringBuilder sb2 = new StringBuilder();

        optional = Optional.empty();
        optional.ifPresent(s -> sb2.append("Ok"));
        Assertions.assertEquals(0, sb2.length());
    }

    @Test
    public void testMap() {
        Optional<String> optional = Optional.of("foo").map(s -> "bar");
        Assertions.assertEquals("bar", optional.get());

        optional = Optional.empty().map(s -> "bar");
        Assertions.assertFalse(optional.isPresent());
    }


    @Test
    public void testFlatMap() {
        Optional<String> optional = Optional.of("foo").flatMap(s -> Optional.of("bar"));
        Assertions.assertEquals("bar", optional.get());

        optional = Optional.empty().flatMap(s -> Optional.of("bar"));
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    public void testOrElse() {
        String s = Optional.of("test").orElse("foo");
        Assertions.assertEquals(s, "test");

        s = Optional.<String>empty().orElse("foo");
        Assertions.assertEquals(s, "foo");
    }

    @Test
    public void testOrElseGet() {
        Assertions.assertEquals(Optional.of("test").orElseGet(() -> "foo"), "test");
        Assertions.assertEquals(Optional.empty().orElseGet(() -> "foo"), "foo");
    }

    @Test
    public void testOrElseThrow() throws Exception {

        String s = Optional.of("test").orElseThrow(Exception::new);
        Assertions.assertEquals(s, "test");

        Assertions.assertThrows(RuntimeException.class, () -> {
            Optional.<String>empty().orElseThrow(RuntimeException::new);
        });
    }
}
