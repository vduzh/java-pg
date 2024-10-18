package by.duzh.jse;

import org.junit.Test;

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
}
