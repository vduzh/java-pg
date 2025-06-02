package by.duzh.jse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.logging.Logger;

class VarArgs {
    // int is a variable-length argument
    public static int sum(int... nums) {
        int res = 0;
        for (int num : nums) {
            res += num;
        }
        return res;
    }

    // ordinary args + variable-length args
    public static int multiply(
            int start,
            // a variable-length argument must be last
            int... nums) {
        int res = start;
        for (int num : nums) {
            res *= num;
        }
        return res;
    }
}

public class VariableLengthArgumentsTests {
    private static final Logger logger = Logger.getLogger(VariableLengthArgumentsTests.class.getName());

    @Test
    public void testWithSeveralVariableLengthArgs() throws Exception {
        int res = VarArgs.sum(1, 2, 3);
        Assertions.assertEquals(6, res);
    }

    @Test
    public void testWithNoArgs() throws Exception {
        int res = VarArgs.sum();
        Assertions.assertEquals(0, res);
    }

    @Test
    public void testSeveralArgs() throws Exception {
        int res = VarArgs.multiply(10, 2, 4);
        Assertions.assertEquals(80, res);
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
