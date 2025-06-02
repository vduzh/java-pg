package by.duzh.jse;

import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

public class LoopTests {
    private static final Logger logger = Logger.getLogger(LoopTests.class.getName());

    @Test
    public void testWhile() throws Exception {
        int i = 0;
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

    @Test
    public void testDoWhile() throws Exception {
        int i = 2;
        do {
            System.out.println("do while: " + i);
            i--;
        } while (i > 0);
    }

    @Test
    public void testFor() throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println("for: " + i);
        }
    }

    @Test
    public void testForEach() throws Exception {
        for (Object obj : new Object[2]) {
            System.out.println("for each");
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
