package by.duzh.jse.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SwitchTest {

    private String _testSwitch(int value) {
        String s;

        switch (value) {
            case 1:
            case 2:
            case 3:
                s = "FIRST";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                s = "SECOND";
                break;
            default:
                s = "UNKNOWN";
        }
        return s;
    }

    @Test
    public void testSwitch() throws Exception {
        Assertions.assertEquals("FIRST", _testSwitch(2));
        Assertions.assertEquals("SECOND", _testSwitch(5));
        Assertions.assertEquals("UNKNOWN", _testSwitch(10));
    }

    private String _testJDK12SwitchExpression(int value) {
        String s;

        switch (value) {
            case 1, 2, 3 -> s = "FIRST";
            case 4, 5, 6, 7 -> s = "SECOND";
            default -> s = "UNKNOWN";
        }
        return s;
    }

    @Test
    public void testJDK12SwitchExpression() throws Exception {
        Assertions.assertEquals("FIRST", _testJDK12SwitchExpression(2));
        Assertions.assertEquals("SECOND", _testJDK12SwitchExpression(5));
        Assertions.assertEquals("UNKNOWN", _testJDK12SwitchExpression(10));
    }

    private String _testJDK13Switch(int value) {
        return switch (value) {
            case 1, 2, 3 -> "FIRST";
            case 4, 5, 6, 7 -> "SECOND";
            default -> "UNKNOWN";
        };
    }

    @Test
    public void testJDK13Switch() throws Exception {
        Assertions.assertEquals("FIRST", _testJDK13Switch(2));
        Assertions.assertEquals("SECOND", _testJDK13Switch(5));
        Assertions.assertEquals("UNKNOWN", _testJDK13Switch(10));
    }

    private String _testJDK13SwitchWithYield(int value) {
        return switch (value) {
            case 1:
            case 2:
            case 3:
                yield "FIRST";
            case 4:
            case 5:
            case 6:
            case 7:
                yield "SECOND";
            default:
                yield "UNKNOWN";
        };
    }

    @Test
    public void testJDK13SwitchWithYield() throws Exception {
        Assertions.assertEquals("FIRST", _testJDK13SwitchWithYield(2));
        Assertions.assertEquals("SECOND", _testJDK13SwitchWithYield(5));
        Assertions.assertEquals("UNKNOWN", _testJDK13SwitchWithYield(10));
    }
}
