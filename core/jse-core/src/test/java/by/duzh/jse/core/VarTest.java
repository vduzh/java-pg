package by.duzh.jse.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Note: type inference
public class VarTest {
    @Test
    public void testJDK10VarWithInitializers() {
        var list = new ArrayList<String>();
        Assertions.assertEquals(ArrayList.class, list.getClass());
    }

    @Test
    public void testJDK10VarWithIndexesInForLoop() {
        int[] numbers = {1, 2, 3};

        for (var i = 0; i < numbers.length; i++) { // for (unt i = 0 ...
        }

        for (var n: numbers) { //for (int n: numbers)
        }
    }

    @Test
    public void testJDK10VarForLambdaParams() {
        var list = Stream.of("foo", "bar").map((var s) -> s.toUpperCase()).collect(Collectors.toList());
        Assertions.assertEquals(list, Arrays.asList("FOO", "BAR"));
    }

}
