package by.duzh.jse;

import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import java.util.Random;

public class OperatorsTests {
    private static final Logger logger = Logger.getLogger(OperatorsTests.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testIf() throws Exception {
        var random = new Random();
        var i = random.nextInt(10);

        if (i < 5) {
            System.out.println("i < 5");
        } else if (i < 8) {
            System.out.println("i < 8");
        } else {
            System.out.println("i >= 8");
        }
    }

    @Test
    public void testSwitch() throws Exception {
        var random = new Random();
        var i = random.nextInt(10);

        switch (i) {
            case 0:
                System.out.println("i == 0");
                break;
            case 1:
                System.out.println("i == 1");
                break;
            default:
                System.out.println("i > 1");
        }
    }

    @Test
    public void testSwitchWithAutoBoxing() throws Exception {
        Integer ibtObj = new Random().nextInt(3);
        switch (ibtObj) {
            case 0:
                System.out.println("Switch: Zero");
                break;
            case 1:
                System.out.println("Switch: One");
                break;
            default:
                System.out.println("Switch: Other");
        }
    }
}
