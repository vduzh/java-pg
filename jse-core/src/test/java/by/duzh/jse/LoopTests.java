package by.duzh.jse;

import org.junit.Test;

public class LoopTests {
    @Test
    public void testWhile() throws Exception {
        int i =0;
        while (i < 2) {
            System.out.println("while: " + i);
            i++;
        }

        // test breaks
        while (true) {
            System.out.println("while with break");
            break;
        }

    }
}
