package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class HashSetTest {
    private HashSet<Integer> set;

    @Test
    public void testCreateEmpty() {
        set = new HashSet<>();
    }

    @Test
    public void testCreateFromCollection() {
        set = new HashSet<>(Arrays.asList(1, 2, 3));
        Assertions.assertEquals(3, set.size());
    }

    @Test
    public void testCreateWithCapacity() {
        set = new HashSet<>(20);
    }

    @Test
    public void testCreateWithCapacityAndFillRatio() {
        set = new HashSet<>(20,0.3f);
    }

    @Test
    public void testSetMethods() {
        set = new HashSet<>();

        set.add(1);
        set.add(2);
        set.add(2);

        Assertions.assertEquals(2, set.size());
    }
}
