package by.duzh.jse.util.optional;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalInt;

//NOTE: it does not inherits from Optional
public class OptionalIntTests {

    public void testOf() {
        OptionalInt optional = OptionalInt.of(123);
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(OptionalInt.of(123).isPresent());
        Assert.assertFalse(OptionalInt.empty().isPresent());
    }

    @Test
    public void testJDK11IsEmpty() {
        Assert.assertTrue(OptionalInt.empty().isEmpty());
        Assert.assertFalse(OptionalInt.of(123).isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGet() {
        Assert.assertEquals(123, OptionalInt.of(123).getAsInt());

        OptionalInt.empty().getAsInt();
    }

    @Test
    public void testEmpty() {
        OptionalInt optional = OptionalInt.empty();
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(OptionalInt.of(1), OptionalInt.of(1));
        Assert.assertNotEquals(OptionalInt.of(1), OptionalInt.of(2));
    }

    @Test
    public void testIfPresent() {
        StringBuilder sb = new StringBuilder();

        OptionalInt optional = OptionalInt.of(1);
        optional.ifPresent(s -> sb.append("Ok"));
        Assert.assertEquals("Ok", sb.toString());

        StringBuilder sb2 = new StringBuilder();

        optional = OptionalInt.empty();
        optional.ifPresent(s -> sb2.append("Ok"));
        Assert.assertEquals(0, sb2.length());
    }

    @Test
    public void testOrElse() {
        int i = OptionalInt.of(1).orElse(2);
        Assert.assertEquals(i, 1);

        i = OptionalInt.<String>empty().orElse(2);
        Assert.assertEquals(i, 2);
    }

    @Test
    public void testOrElseGet() {
        Assert.assertEquals(OptionalInt.of(1).orElseGet(() -> 2), 1);
        Assert.assertEquals(OptionalInt.empty().orElseGet(() -> 2), 2);
    }

    @Test(expected = RuntimeException.class)
    public void testOrElseThrowNoException() throws Exception {

        int i = OptionalInt.of(1).orElseThrow(Exception::new);
        Assert.assertEquals(i, 1);

        i = OptionalInt.<String>empty().orElseThrow(RuntimeException::new);
    }
}
