package by.duzh.jse.core;

import org.junit.Assert;
import org.junit.Test;

public class TextBlocksTest {

    @Test
    public void testJDK13() throws Exception {
        String smallBlock = """
                1
                2
                """;
        Assert.assertEquals("1\n2\n", smallBlock);

        smallBlock = """
                1
                2
                  """;
        Assert.assertEquals("1\n2\n", smallBlock);

        smallBlock = """
              1
              2
            """;
        Assert.assertEquals("  1\n  2\n", smallBlock);

        smallBlock = """
                1
                2""";
        Assert.assertEquals("1\n2", smallBlock);
    }

    @Test
    public void testJDK14LineTerminator() throws Exception {
        String text = """
                This is major Tom to Ground Control \
                I am stepping through the door...
                Wait… What???
                """;
        Assert.assertEquals(2, text.lines().count());
    }

    @Test
    public void testJDK14SingleSpace() throws Exception {
        String text = """
                line1  
                line2\s
                line3
                """;
        Assert.assertEquals("line1\nline2 \nline3\n", text);
    }
}
