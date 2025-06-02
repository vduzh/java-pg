package by.duzh.jse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayTests {
    @Test
    public void testCreate() throws Exception {
        // init with default values
        // for classes
        Object[] objects = new Object[1];
        Assertions.assertNull(objects[0]);

        // for primitives
        int[] numbers = new int[2];
        Assertions.assertEquals(0, numbers[0]);
        Assertions.assertEquals(0, numbers[1]);

        // init each element
        numbers = new int[1];
        numbers[0] = 1;
        Assertions.assertEquals(1, numbers[0]);

        numbers = new int[]{10};
        Assertions.assertEquals(10, numbers[0]);

        // init while creating
        int[] second = {100};
        Assertions.assertEquals(100, second[0]);
    }

    @Test
    public void testMembers() throws Exception {
        int[] numbers = {1, 2, 3, 4, 5};
        Assertions.assertEquals(5, numbers.length);
    }

    @Test
    public void testArrayIndexOutOfBoundsException() throws Exception {
        Object[] objects = new Object[2];
        try {
            objects[3] = 3;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testArrayIndexOutOfBoundsException: ArrayIndexOutOfBoundsException");
        }
    }
}
