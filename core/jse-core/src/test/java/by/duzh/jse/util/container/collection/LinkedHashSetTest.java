package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetTest {
    private LinkedHashSet<Integer> set;

    @Test
    public void testCreateEmpty() {
        set = new LinkedHashSet<>();
    }

    @Test
    public void testCreateFromCollection() {
        set = new LinkedHashSet<>(Arrays.asList(1, 2, 3));
        Assertions.assertEquals(3, set.size());
    }

    @Test
    public void testCreateWithCapacity() {
        set = new LinkedHashSet<>(20);
    }

    @Test
    public void testCreateWithCapacityAndFillRatio() {
        set = new LinkedHashSet<>(20,0.3f);
    }

    @Test
    public void testElementsOrder() {
        set = new LinkedHashSet<>();

        set.add(1);
        set.add(2);
        set.add(2);
        set.add(3);

        Assertions.assertEquals(3, set.size());
        Iterator<Integer> iterator = set.iterator();
        for (int i = 1; i <= 3; i++) {
            Assertions.assertEquals(i, iterator.next().intValue());
        }
    }
}
