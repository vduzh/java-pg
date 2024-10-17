package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Random;

public class RandomTest {
    private Random random;

    @Before
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
        random.nextBoolean();

        byte[] bytes = new byte[16];
        random.nextBytes(bytes);

        double d = random.nextDouble();
        double f = random.nextFloat();
        double g = random.nextGaussian();

        int i = random.nextInt();
        i = random.nextInt(100);

        long l = random.nextLong();
        System.out.println(l);
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

        Assert.assertEquals(10, random.ints(10).count());
        Assert.assertEquals(10, random.ints(10, 10, 20).count());
    }

    @Test
    public void testJDK8Longs() {
        OptionalLong optional = random.longs().findFirst();
    }

}
