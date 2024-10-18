package by.duzh.jse.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StringTest {
    @Test
    public void testCreate() throws Exception{
        String s = "Hello World!";
        System.out.println("create: " + s);
    }

    @Test
    public void testSpecialSymbols() throws Exception{
        String s = "a\n";
        s = "a\"";
        System.out.println(s);
    }

    @Test
    public void testConcat() throws Exception{
        Assert.assertEquals("test world", "test " + "world");
        Assert.assertEquals("test1", "test" + 1);
    }

    @Test
    public void testJDK11IsBlank() {
        Assert.assertTrue("".isBlank());
        Assert.assertTrue("  ".isBlank());
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
    public void testToSharArray() {
        Assert.assertEquals("Hello", String.valueOf("Hello".toCharArray()));
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
    public void testCompareTo() {
        Assert.assertTrue("aaa".compareTo("bb") < 0);
    }

    @Test
    public void testReplaceChars() {
        Assert.assertEquals("abc".replace('b', '-'), "a-c");
    }

    @Test
    public void testReplaceCharSequence() {
        Assert.assertEquals("abcdefj".replace("cde", "kk"), "abkkfj");
    }

    @Test
    public void testJDK8Join() {
        Assert.assertEquals(String.join("-", "one", "two", "ten"), "one-two-ten");
    }

    @Test
    public void testJDK8JoinIterable() {
        var strings = Arrays.asList("one", "two", "ten");
        Assert.assertEquals(String.join("-", strings), "one-two-ten");
    }

    @Test
    public void testJDK11Lines() {
        var s = new Formatter().format("Hello%nWorld!").toString();
        var list = s.lines().collect(Collectors.toList());

        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Hello", list.get(0));
        Assert.assertEquals("World!", list.get(1));
    }

    @Test
    public void testJDK11Repeat() {
        var s = "foo".repeat(3);
        Assert.assertEquals("foofoofoo", s);
    }

    @Test
    public void testJDK11Strip() {
        var s = "   Hello World!  ".strip();
        Assert.assertEquals("Hello World!", s);
    }

    @Test
    public void testJDK11StripLeading() {
        var s = "  Hello World!  ".stripLeading();
        Assert.assertEquals("Hello World!  ", s);
    }

    @Test
    public void testJDK11StripTrailing() {
        var s = "  Hello World!  ".stripTrailing();
        Assert.assertEquals("  Hello World!", s);
    }

    @Test
    public void testJDK15Formatted() throws Exception {
        Assert.assertEquals("Hello John!", "Hello %s!".formatted("John"));
    }

    @Test
    public void testJDK15StripIndent() throws Exception {
        var s = """
                Hello
                World!
                """;

        Assert.assertEquals("Hello\nWorld!\n", s.stripIndent());
    }
}
