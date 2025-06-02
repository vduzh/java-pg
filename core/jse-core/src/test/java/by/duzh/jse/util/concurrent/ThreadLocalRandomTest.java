package by.duzh.jse.util.concurrent;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

@Disabled
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
