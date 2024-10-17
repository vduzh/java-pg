package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SetTest {
    private Set<Integer> set;

    @Before
    public void init() {
        set = new HashSet<>();
    }

    @Test
    public void testAdd() {
        Assert.assertTrue(set.add(1));
        Assert.assertFalse(set.add(1));
    }

    @Test
    public void testAddAll() {
        Assert.assertTrue(set.addAll(Arrays.asList(1, 2)));
        Assert.assertTrue(set.addAll(Arrays.asList(1, 3)));
        Assert.assertFalse(set.addAll(Arrays.asList(2, 3)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOf() throws Exception {
        // unmodifiable set
        set = Set.of(1, 2, 3);
        set.add(4);
    }

    @Test
    public void testJDK10CopyOf() {
        Collection<Integer> src = Collections.nCopies(1, 10);
        var integers = Set.copyOf(src);
        Assert.assertArrayEquals(src.toArray(), integers.toArray());
    }
}
