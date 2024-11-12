package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;

public class EnumSetTest {
    enum SomeEnum {
        ONE,
        TWO,
        THREE
    }

    private EnumSet<SomeEnum> set;

    @Test
    public void testAllOf() {
        set = EnumSet.allOf(SomeEnum.class);

        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.TWO, SomeEnum.THREE)));
    }

    @Test
    public void testComplementOf() {
        EnumSet<SomeEnum> es = EnumSet.of(SomeEnum.TWO);

        set = EnumSet.complementOf(es);
        Assert.assertEquals(2, set.size());
        Assert.assertTrue(set.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.THREE)));
    }

    @Test
    public void testCopyOf() {
        set = EnumSet.of(SomeEnum.ONE, SomeEnum.THREE);

        EnumSet<SomeEnum> copy = EnumSet.copyOf(set);

        Assert.assertEquals(2, copy.size());
        Assert.assertTrue(copy.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.THREE)));
    }

    @Test
    public void testCopOfCollection() {
        set = EnumSet.copyOf(Arrays.asList(SomeEnum.ONE, SomeEnum.THREE));

        Assert.assertEquals(2, set.size());
        Assert.assertTrue(set.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.THREE)));
    }

    @Test
    public void testNoneOf() {
        set = EnumSet.noneOf(SomeEnum.class);
        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void testOf() {
        set = EnumSet.of(SomeEnum.ONE, SomeEnum.THREE);

        Assert.assertEquals(2, set.size());
        Assert.assertTrue(set.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.THREE)));
    }

    @Test
    public void testRange() {
        set = EnumSet.range(SomeEnum.ONE, SomeEnum.THREE);

        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.containsAll(Arrays.asList(SomeEnum.ONE, SomeEnum.TWO, SomeEnum.THREE)));
    }
}
