package by.duzh.jse;

import org.junit.Assert;
import org.junit.Test;

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
    @Test
    public void testWithSeveralVariableLengthArgs() throws Exception {
        int res = VarArgs.sum(1, 2, 3);
        Assert.assertEquals(6, res);
    }

    @Test
    public void testWithNoArgs() throws Exception {
        int res = VarArgs.sum();
        Assert.assertEquals(0, res);
    }

    @Test
    public void testSeveralArgs() throws Exception {
        int res = VarArgs.multiply(10, 2, 4);
        Assert.assertEquals(80, res);
    }

}
