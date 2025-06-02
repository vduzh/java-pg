package by.duzh.jse.util.optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertTrue(OptionalInt.of(123).isPresent());
        Assertions.assertFalse(OptionalInt.empty().isPresent());
    }

    @Test
    public void testJDK11IsEmpty() {
        Assertions.assertTrue(OptionalInt.empty().isEmpty());
        Assertions.assertFalse(OptionalInt.of(123).isEmpty());
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(123, OptionalInt.of(123).getAsInt());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            OptionalInt.empty().getAsInt();
        });
    }

    @Test
    public void testEmpty() {
        OptionalInt optional = OptionalInt.empty();
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(OptionalInt.of(1), OptionalInt.of(1));
        Assertions.assertNotEquals(OptionalInt.of(1), OptionalInt.of(2));
    }

    @Test
    public void testIfPresent() {
        StringBuilder sb = new StringBuilder();

        OptionalInt optional = OptionalInt.of(1);
        optional.ifPresent(s -> sb.append("Ok"));
        Assertions.assertEquals("Ok", sb.toString());

        StringBuilder sb2 = new StringBuilder();

        optional = OptionalInt.empty();
        optional.ifPresent(s -> sb2.append("Ok"));
        Assertions.assertEquals(0, sb2.length());
    }

    @Test
    public void testOrElse() {
        int i = OptionalInt.of(1).orElse(2);
        Assertions.assertEquals(i, 1);

        i = OptionalInt.<String>empty().orElse(2);
        Assertions.assertEquals(i, 2);
    }

    @Test
    public void testOrElseGet() {
        Assertions.assertEquals(OptionalInt.of(1).orElseGet(() -> 2), 1);
        Assertions.assertEquals(OptionalInt.empty().orElseGet(() -> 2), 2);
    }

    @Test
    public void testOrElseThrow() throws Exception {

        int i = OptionalInt.of(1).orElseThrow(Exception::new);
        Assertions.assertEquals(i, 1);

        Assertions.assertThrows(RuntimeException.class, () -> {
            OptionalInt.empty().orElseThrow(RuntimeException::new);
        });
    }
}
