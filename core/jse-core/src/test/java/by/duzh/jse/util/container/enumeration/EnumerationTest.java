package by.duzh.jse.util.container.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

// Enumeration is replaced with Iterator
public class EnumerationTest {
    private Enumeration<Integer> enumeration;

    @BeforeEach
    public void setUp() {
        enumeration = new Vector<Integer>(Arrays.asList(10, 20)).elements();
    }

    @Test
    public void testHasMoreElements() {
        Assertions.assertTrue(enumeration.hasMoreElements());
    }

    @Test
    public void testNext() {
        Assertions.assertEquals(10, enumeration.nextElement().intValue());
        Assertions.assertEquals(20, enumeration.nextElement().intValue());
    }
}
