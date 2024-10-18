package by.duzh.jse;

import org.junit.Assert;
import org.junit.Test;

public class ArrayTests {
    @Test
    public void testCreate() throws Exception {
        Object[] objects = new Object[2];
    }

    @Test
    public void testInit() throws Exception {
        // init with default values
        // for classes
        Object[] objects = new Object[1];
        Assert.assertNull(objects[0]);

        // for primitives
        int[] numbers = new int[2];
        Assert.assertEquals(0, numbers[0]);
        Assert.assertEquals(0, numbers[1]);

        // init each element
        numbers = new int[1];
        numbers[0] = 1;
        Assert.assertEquals(1, numbers[0]);

        // init while creating
        numbers = new int[] {10};
        Assert.assertEquals(10, numbers[0]);
    }

    @Test
    public void testMembers() throws Exception {
        int[] numbers = new int[] {1, 2, 3, 4, 5};

        Assert.assertEquals(5, numbers.length);
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
