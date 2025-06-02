package by.duzh.jse.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.StringTokenizer;

public class StringTokenizerTest {
    private StringTokenizer tokenizer;

    @Test
    public void testCreateWithDefaultDelimiter() {
        tokenizer = new StringTokenizer("Who are you?");
    }

    @Test
    public void testCreateWithOneDelimiter() {
        tokenizer = new StringTokenizer("Who-are-you?", "-");
    }

    @Test
    public void testCreateWithSeveralDelimiter() {
        tokenizer = new StringTokenizer("Who-are&you?", "&-");
    }

    @Test
    public void testCreateAndReturnDelimiters() {
        tokenizer = new StringTokenizer("Who-are&you?", "&-", true);
    }

    @Test
    public void testCountTokens() {
        tokenizer = new StringTokenizer("Who are you?");
        Assertions.assertEquals(3, tokenizer.countTokens());
    }

    @Test
    public void testHasMoreElements() {
        tokenizer = new StringTokenizer("Who are you?");
        Assertions.assertTrue(tokenizer.hasMoreElements());
    }

    @Test
    public void testHasMoreTokens() {
        tokenizer = new StringTokenizer("Who are you?");
        Assertions.assertTrue(tokenizer.hasMoreTokens());
    }

    @Test
    public void testNextElement() {
        tokenizer = new StringTokenizer("Who&are-you?1", "&-?", true);

        Assertions.assertEquals("Who", tokenizer.nextElement());
        Assertions.assertEquals("&", tokenizer.nextElement());
        Assertions.assertEquals("are", tokenizer.nextElement());
        Assertions.assertEquals("-", tokenizer.nextElement());
        Assertions.assertEquals("you", tokenizer.nextElement());
        Assertions.assertEquals("?", tokenizer.nextElement());
        Assertions.assertEquals("1", tokenizer.nextElement());
    }

    @Test
    public void testNextToken() {
        tokenizer = new StringTokenizer("Who are you?");

        Assertions.assertEquals("Who", tokenizer.nextToken());
        Assertions.assertEquals("are", tokenizer.nextToken());
        Assertions.assertEquals("you?", tokenizer.nextToken());
    }
}
