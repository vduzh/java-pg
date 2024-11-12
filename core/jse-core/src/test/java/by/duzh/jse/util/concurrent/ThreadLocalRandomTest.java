package by.duzh.jse.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {
    @Test
    public void current() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
    }

    @Test
    public void nextInt() {
        // TODO: Apply the ThreadLocalRandom.current().nextInt in tests!!
        final var i = ThreadLocalRandom.current().nextInt(10);
        System.out.println(i);
    }


}
