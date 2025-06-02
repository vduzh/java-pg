package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Random;

public class RandomTest {
    private Random random;

    @BeforeEach
    public void init() {
        random = new Random();
    }

    @Test
    public void testCreate() {
        random = new Random();
        random = new Random(123);
    }

    @Test
    public void testNext() {
        boolean b = random.nextBoolean();
        System.out.println("testNext: boolean = " + b);

        double d = random.nextDouble();
        System.out.println("testNext: double = " + d);
        double f = random.nextFloat();
        System.out.println("testNext: float = " + f);
        double g = random.nextGaussian();
        System.out.println("testNext: gaussian = " + g);

        int i = random.nextInt();
        System.out.println("testNext: int = " + i);
        i = random.nextInt(100);
        System.out.println("testNext: int = " + i);

        long l = random.nextLong();
        System.out.println("testNext: long = " + l);

        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
    }

    @Test
    public void testSetSeed() {
        random.setSeed(1234L);
    }

    @Test
    public void testJDK8Doubles() {
        OptionalDouble optional = random.doubles().findFirst();
    }

    @Test
    public void testJDK8Ints() {
        OptionalInt optional = random.ints().findFirst();

        Assertions.assertEquals(10, random.ints(10).count());
        Assertions.assertEquals(10, random.ints(10, 10, 20).count());
    }

    @Test
    public void testJDK8Longs() {
        OptionalLong optional = random.longs().findFirst();
    }
}
