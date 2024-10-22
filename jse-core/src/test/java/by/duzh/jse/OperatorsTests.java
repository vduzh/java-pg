package by.duzh.jse;

import org.junit.Test;

import java.util.Random;

public class OperatorsTests {
    @Test
    public void testIf() throws Exception {
        int[] data = {-10, 20, 35};

        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                System.out.println(data[i] + ": " + "It's cold!");
            } else if (data[i] < 30) {
                System.out.println(data[i] + ": " + "It's OK!");
            } else {
                System.out.println(data[i] + ": " + "It's too hoot!");
            }
        }
    }

    @Test
    public void testSwitch() throws Exception {
        int i = new Random().nextInt();
        switch (i) {
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
