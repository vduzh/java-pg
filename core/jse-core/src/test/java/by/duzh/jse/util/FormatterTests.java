package by.duzh.jse.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class FormatterTests {
    private Formatter formatter;
    private Date TEST_DATE;

    @BeforeEach
    public void init() {
        formatter = new Formatter();

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2020, Calendar.MARCH, 8, 10, 30, 15);
        TEST_DATE = calendar.getTime();
    }

    @AfterEach
    public void cleanUp() {
        if (formatter != null) {
            formatter.close();
        }
    }

    @Test
    public void testSimpleFormat() {
        formatter.format("string is %s, digit is %d", "foo", 5);
        Assertions.assertEquals("string is foo, digit is 5", formatter.toString());
    }

    @Test
    public void testString() {
        formatter.format("string is %S", "foo");
        Assertions.assertEquals("string is FOO", formatter.toString());
    }

    @Test
    public void testNumbers() {
        formatter.format("digit is %d", 5);
        Assertions.assertEquals("digit is 5", formatter.toString());
    }

    @Test
    public void testDate() {
        formatter.format("%tD", TEST_DATE);
        Assertions.assertEquals("03/08/20", formatter.toString());
        formatter.close();

        formatter = new Formatter();
        formatter.format("%tF", TEST_DATE);
        Assertions.assertEquals("2020-03-08", formatter.toString());
    }

    @Test
    public void testMinimumFieldWidth() {
        formatter.format("%3d", 1);
        Assertions.assertEquals("  1", formatter.toString());
        formatter.close();

        formatter = new Formatter();
        formatter.format("%03d", 1);
        Assertions.assertEquals("001", formatter.toString());
    }

    @Test
    public void testFormatFlags() {
        // Justifying Output
        formatter.format("|%-5d|%5d|", 123, 456);
        Assertions.assertEquals("|123  |  456|", formatter.toString());
        formatter.close();

        // + sign to be shown before positive numeric values
        formatter = new Formatter();
        formatter.format("%+d|%+d", 123, -456);
        Assertions.assertEquals("+123|-456", formatter.toString());
        formatter.close();

        // output a space before positive values
        formatter = new Formatter();
        formatter.format("% d|% d", 123, -456);
        Assertions.assertEquals(" 123|-456", formatter.toString());
        formatter.close();

        // show negative numeric output inside parentheses
        formatter = new Formatter();
        formatter.format("%(d|%(d", 123, -456);
        Assertions.assertEquals("123|(456)", formatter.toString());
        formatter.close();

        // Comma Flag
        formatter = new Formatter(Locale.US);
        formatter.format("%,d", 1123456789);
        Assertions.assertEquals("1,123,456,789", formatter.toString());
    }

    @Test
    public void testArgumentIndex() {
        formatter.format("%d-%s, %1$d-%2$s", 1, "foo");
        Assertions.assertEquals("1-foo, 1-foo", formatter.toString());
    }

    @Test
    public void testClosingFormatter() {
        try (Formatter formatter = new Formatter()) {
            formatter.format("%d", 1);
        }
    }

    @Test
    public void testPrintf() {
        //System.out.printf("%d and %s", 123, "foo");
    }
}
