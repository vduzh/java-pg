package by.duzh.jse.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

//TODO: test the remaining methods
public class Base64Test {
    @Test
    public void testGetEncoder() {
        String s = "Hello!";
        byte[] encoded = Base64.getEncoder().encode(s.getBytes());

        Assertions.assertEquals(s, new String(Base64.getDecoder().decode(encoded)));
    }

}
