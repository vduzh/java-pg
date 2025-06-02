package by.duzh.jse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

public class AutoBoxingTests {
    @Test
    public void manualBoxingAndUnboxing() throws Exception {
        // boxing
        Integer intObj = Integer.valueOf(100);

        // unboxing
        int i = intObj.intValue();
    }

    @Test
    public void autoBoxingAndUnboxing() {
        // a compiler does that automatically

        // auto boxing
        Integer intObj = 100;

        // auto unboxing
        int i = intObj;
    }

    @Test
    public void withMethods() {
        class AutoBox {
            static int sum(Integer i, int j) {
                return i + j;
            }
        }

        // use autoboxing on the methods params
        final var j = Integer.valueOf(20);
        Integer res = AutoBox.sum(10, j);

        Assertions.assertEquals(30, res.intValue());
    }

    @Test
    public void inExpressions() {
        // use autoboxing
        final var intObj = Integer.valueOf(20);
        Integer res = 10 + intObj;

        Assertions.assertEquals(30, res.intValue());
    }

    @Test
    public void inIf() {
        Boolean boolObj = new Random().nextBoolean();

        if (boolObj) {
            System.out.println("Ok");
        } else {
            System.out.println("False");
        }
    }
}
