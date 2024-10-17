package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
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
        Assert.assertEquals(3, tokenizer.countTokens());
    }

    @Test
    public void testHasMoreElements() {
        tokenizer = new StringTokenizer("Who are you?");
        Assert.assertTrue(tokenizer.hasMoreElements());
    }

    @Test
    public void testHasMoreTokens() {
        tokenizer = new StringTokenizer("Who are you?");
        Assert.assertTrue(tokenizer.hasMoreTokens());
    }

    @Test
    public void testNextElement() {
        tokenizer = new StringTokenizer("Who&are-you?1", "&-?", true);

        Assert.assertEquals("Who", tokenizer.nextElement());
        Assert.assertEquals("&", tokenizer.nextElement());
        Assert.assertEquals("are", tokenizer.nextElement());
        Assert.assertEquals("-", tokenizer.nextElement());
        Assert.assertEquals("you", tokenizer.nextElement());
        Assert.assertEquals("?", tokenizer.nextElement());
        Assert.assertEquals("1", tokenizer.nextElement());
    }

    @Test
    public void testNextToken() {
        tokenizer = new StringTokenizer("Who are you?");

        Assert.assertEquals("Who", tokenizer.nextToken());
        Assert.assertEquals("are", tokenizer.nextToken());
        Assert.assertEquals("you?", tokenizer.nextToken());
    }
}
