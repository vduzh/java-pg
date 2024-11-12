package by.duzh.jse.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringTest {
    @Test
    public void testCreate() throws Exception{
        String s = "Hello World!";
    }

    @Test
    public void testSpecialSymbols() throws Exception{
        String s = "a\n";
        s = "a\"";
    }

    @Test
    public void testValueOfInt() {
        Assert.assertEquals("123", String.valueOf(123));
    }

    @Test
    public void testValueOfBoolean() {
        Assert.assertEquals("true", String.valueOf(true));
    }

    @Test
    public void testValueOfChars() {
        char[] chars = {'h', 'e', 'l', 'l', 'o'};
        Assert.assertEquals("ll", String.valueOf(chars, 2, 2));
    }

    @Test
    public void testConcat() throws Exception{
        Assert.assertEquals("test world", "test " + "world");
        Assert.assertEquals("test1", "test" + 1);
    }

    @Test
    public void testToCharArray() {
        char[] chars = "ab".toCharArray();
        Assert.assertEquals('a', chars[0]);
        Assert.assertEquals('b', chars[1]);
    }

    @Test
    public void testGetChars() {
        var s = "Hello";
        var chars = new char[5];
        s.getChars(0, 5, chars, 0);
        Assert.assertEquals(s, String.valueOf(chars));
    }

    @Test
    public void testGetSubstringChars() {
        var s = "Hello World!";
        char[] chars = {'?', 0, 0, 0, 0, 0, '?'};

        s.getChars(6, 11, chars, 1);
        Assert.assertEquals("?World?", String.valueOf(chars));
    }

    @Test
    public void testRegionMatchesOK() {
        Assert.assertTrue("Hello World!".regionMatches(6, "My World is good!", 3, 5));
    }

    @Test
    public void testRegionMatchesFalse() {
        Assert.assertFalse("Hello World!".regionMatches(6, "My World is good!", 0, 5));
    }

    @Test
    public void testCompare() throws Exception {
        String one = "test";
        String two = "test";
        System.out.println(one.equals(two));
    }

    @Test
    public void testCompareTo() {
        Assert.assertTrue("aaa".compareTo("bb") < 0);
        Assert.assertEquals(0, "aa".compareTo("aa"));
        Assert.assertTrue("bb".compareTo("aa") > 0);
    }

    @Test
    public void testReplaceChars() {
        Assert.assertEquals("abc".replace('b', '-'), "a-c");
    }

    @Test
    public void testReplaceCharSequence() {
        Assert.assertEquals("abcdef".replace("cde", "kk"), "abkkf");
    }

    @Test
    public void testJoin() {
        Assert.assertEquals("one-two-ten", String.join("-", "one", "two", "ten"));
    }

    @Test
    public void testJoinIterable() {
        var strings = Arrays.asList("one", "two", "ten");
        Assert.assertEquals("one-two-ten", String.join("-", strings));
    }

    @Test
    public void testIsBlank() {
        // for empty string
        Assert.assertTrue("".isBlank());
        // for the string full of spaces
        Assert.assertTrue("  ".isBlank());
    }

    @Test
    public void testLines() {
        var s = new Formatter().format("Hello%nWorld!").toString();

        Stream<String> stringStream = s.lines();
        var list = stringStream.toList();

        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Hello", list.get(0));
        Assert.assertEquals("World!", list.get(1));
    }

    @Test
    public void testRepeat() {
        var s = "foo".repeat(3);
        Assert.assertEquals("foofoofoo", s);
    }

    @Test
    public void testStrip() {
        var s = "   Hello World!  ".strip();
        Assert.assertEquals("Hello World!", s);
    }

    @Test
    public void testStripLeading() {
        var s = "  Hello World!  ".stripLeading();
        Assert.assertEquals("Hello World!  ", s);
    }

    @Test
    public void testStripTrailing() {
        var s = "  Hello World!  ".stripTrailing();
        Assert.assertEquals("  Hello World!", s);
    }

    @Test
    public void testStripIndent() throws Exception {
        var s = """
                Hello
                World!
                """;

        Assert.assertEquals("Hello\nWorld!\n", s.stripIndent());
    }

    @Test
    public void testFormatted() throws Exception {
        Assert.assertEquals("Hello John!", "Hello %s!".formatted("John"));
    }
}
