package by.duzh.jse.util.container.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListTest {
    private ArrayList<Integer> list;

    @Test
    public void testCreateEmptyArrayList() {
        list = new ArrayList<>();
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testCreateArrayListWithCollection() {
        list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Assertions.assertEquals(3, list.size());
    }

    @Test
    public void testCreateArrayListOfInitialCapacity() {
        list = new ArrayList<>(1000);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void testEnsureCapacity() {
        list = new ArrayList<>(10);
        list.ensureCapacity(1000);
    }

    @Test
    public void testTrimToSize() {
        list = new ArrayList<>(1000);
        list.addAll(Arrays.asList(1, 2, 3));

        list.trimToSize();
    }

    @Test
    public void testToArrayOfIntegers() {
        list = new ArrayList<>(Arrays.asList(1, 2, 3));

        Integer[] result = list.toArray(new Integer[list.toArray().length]);

        Assertions.assertEquals(1, result[0].intValue());
        Assertions.assertEquals(2, result[1].intValue());
        Assertions.assertEquals(3, result[2].intValue());
    }

    @Test
    public void testToArrayOfIntegersWithLongerArray() {
        list = new ArrayList<>(Arrays.asList(1, 2, 3));

        Integer[] a = {-1, -1, -1, -1, -1, -1}; // 6 items
        Integer[] result = list.toArray(a);

        Assertions.assertEquals(1, result[0].intValue());
        Assertions.assertEquals(2, result[1].intValue());
        Assertions.assertEquals(3, result[2].intValue());
        Assertions.assertNull(result[3]); // TODO: why they set only this value to null???
        Assertions.assertEquals(-1, result[4].intValue());
        Assertions.assertEquals(-1, result[5].intValue());
    }
}
