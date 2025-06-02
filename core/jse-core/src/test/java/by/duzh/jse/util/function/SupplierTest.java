package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.*;

public class SupplierTest {

    @Test
    public void testSupplier() {
        Supplier<String> supplier = () -> "foo";
        Assertions.assertEquals("foo", supplier.get());
    }

    @Test
    public void testBooleanSupplier() {
        BooleanSupplier supplier = () -> false;
        Assertions.assertFalse(supplier.getAsBoolean());
    }

    @Test
    public void testIntSupplier() {
        IntSupplier supplier = () -> 123;
        Assertions.assertEquals(123, supplier.getAsInt());
    }

    @Test
    public void testLongSupplier() {
        LongSupplier supplier = () -> 123;
        Assertions.assertEquals(123, supplier.getAsLong());
    }

    @Test
    public void testDoubleSupplier() {
        DoubleSupplier supplier = () -> 123.4;
        Assertions.assertEquals(123.4, supplier.getAsDouble(), 0.01);
    }
}
