package by.duzh.jse.util.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.*;

public class ConsumerTest {
    private String[] result = {""};

    @Test
    public void testConsumer() {
        Consumer<Integer> consumer = i -> result[0] = "ok";
        consumer.accept(1);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testBiConsumer() {
        BiConsumer<Integer, String> consumer = (i, s) -> result[0] ="ok";
        consumer.accept(1, "foo");
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testIntConsumer() {
        IntConsumer consumer = d -> result[0] = "ok";
        consumer.accept(1);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testLongConsumer() {
        LongConsumer consumer = l -> result[0] = "ok";
        consumer.accept(1);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testDoubleConsumer() {
        DoubleConsumer consumer = d -> result[0] = "ok";
        consumer.accept(1.2);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testObjDoubleConsumer() {
        ObjDoubleConsumer<UUID> consumer = (o, d) -> result[0] = "ok";
        consumer.accept(UUID.randomUUID(), 1.2);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testObjIntConsumer() {
        ObjIntConsumer<UUID> consumer = (o, d) -> result[0] = "ok";
        consumer.accept(UUID.randomUUID(), 1);
        Assertions.assertEquals("ok", result[0]);
    }

    @Test
    public void testObjLongConsumer() {
        ObjLongConsumer<UUID> consumer = (o, d) -> result[0] = "ok";
        consumer.accept(UUID.randomUUID(), 1);
        Assertions.assertEquals("ok", result[0]);
    }

}
