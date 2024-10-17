package by.duzh.jse.util.container.enumeration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

// Enumeration is replaced with Iterator
public class EnumerationTest {
    private Enumeration<Integer> enumeration;

    @Before
    public void setUp() {
        enumeration = new Vector<Integer>(Arrays.asList(10, 20)).elements();
    }

    @Test
    public void testHasMoreElements() {
        Assert.assertTrue(enumeration.hasMoreElements());
    }

    @Test
    public void testNext() {
        Assert.assertEquals(10, enumeration.nextElement().intValue());
        Assert.assertEquals(20, enumeration.nextElement().intValue());
    }
}
