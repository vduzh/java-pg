package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

// Enumeration is replaced with Iterator
// NOTE: Not all methods tested as it is an old class
public class VectorTest {
    private Vector<Integer> vector;

    @BeforeEach
    public void setUp() {
        vector = new Vector<Integer>();
    }

    @Test
    public void testCreate() {
        vector = new Vector<Integer>();
    }

    @Test
    public void testCreateWithCollection() {
        vector = new Vector<Integer>(Arrays.asList(1, 2));
    }

    @Test
    public void testCapacity() {
        vector = new Vector<Integer>(10);

        Assertions.assertEquals(10, vector.capacity());
        Assertions.assertEquals(0, vector.size());
    }

    @Test
    public void testCopyInto() {
        Integer[] dest = {10, 11, 13};

        vector = new Vector<Integer>(Arrays.asList(1, 2, 3));

        vector.copyInto(dest);

        Assertions.assertEquals(1, dest[0].intValue());
        Assertions.assertEquals(2, dest[1].intValue());
    }

    @Test
    public void testAddElement() {
        vector.addElement(123);

        Assertions.assertEquals(1, vector.size());
    }

    @Test
    public void testElementAt() {
        vector.addElement(1);
        vector.addElement(2);

        Assertions.assertEquals(1, vector.elementAt(0).intValue());
        Assertions.assertEquals(2, vector.elementAt(1).intValue());
    }

    @Test
    public void testFirstElement() {
        vector.addElement(1);
        vector.addElement(2);

        Assertions.assertEquals(1, vector.firstElement().intValue());
    }

    @Test
    public void testLastElement() {
        vector.addElement(1);
        vector.addElement(2);

        Assertions.assertEquals(2, vector.lastElement().intValue());
    }

    @Test
    public void testIndexOf() {
        vector.addElement(1);
        vector.addElement(2);
        vector.addElement(3);

        Assertions.assertEquals(1, vector.indexOf(2));
    }

    @Test
    public void testLastIndexOf() {
        vector.addElement(1);
        vector.addElement(2);
        vector.addElement(2);
        vector.addElement(3);

        Assertions.assertEquals(2, vector.lastIndexOf(2));
    }

    @Test
    public void testRemoveElement() {
        vector.addElement(1);
        vector.addElement(2);
        vector.addElement(2);
        vector.addElement(3);

        Assertions.assertTrue(vector.removeElement(3));
        Assertions.assertFalse(vector.removeElement(30));
    }

    @Test
    public void testRemoveElementAt() {
        vector.addElement(1);
        vector.addElement(2);
        vector.addElement(2);
        vector.addElement(3);

        vector.removeElementAt(3);

        Assertions.assertEquals(3, vector.size());
    }
}
