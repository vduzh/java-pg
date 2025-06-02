package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SetTest {
    private Set<Integer> set;

    @BeforeEach
    public void init() {
        set = new HashSet<>();
    }

    @Test
    public void testAdd() {
        Assertions.assertTrue(set.add(1));
        Assertions.assertFalse(set.add(1));
    }

    @Test
    public void testAddAll() {
        Assertions.assertTrue(set.addAll(Arrays.asList(1, 2)));
        Assertions.assertTrue(set.addAll(Arrays.asList(1, 3)));
        Assertions.assertFalse(set.addAll(Arrays.asList(2, 3)));
    }

    @Test
    public void testOf() {
        // unmodifiable set
        set = Set.of(1, 2, 3);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> set.add(4));
    }

    @Test
    public void testJDK10CopyOf() {
        Collection<Integer> src = Collections.nCopies(1, 10);
        var integers = Set.copyOf(src);
        Assertions.assertArrayEquals(src.toArray(), integers.toArray());
    }
}
