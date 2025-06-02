package by.duzh.jse.lang;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
        Assertions.assertEquals("123", String.valueOf(123));
    }

    @Test
    public void testValueOfBoolean() {
        Assertions.assertEquals("true", String.valueOf(true));
    }

    @Test
    public void testValueOfChars() {
        char[] chars = {'h', 'e', 'l', 'l', 'o'};
        Assertions.assertEquals("ll", String.valueOf(chars, 2, 2));
    }

    @Test
    public void testConcat() throws Exception{
        Assertions.assertEquals("test world", "test " + "world");
        Assertions.assertEquals("test1", "test" + 1);
    }

    @Test
    public void testToCharArray() {
        char[] chars = "ab".toCharArray();
        Assertions.assertEquals('a', chars[0]);
        Assertions.assertEquals('b', chars[1]);
    }

    @Test
    public void testGetChars() {
        var s = "Hello";
        var chars = new char[5];
        s.getChars(0, 5, chars, 0);
        Assertions.assertEquals(s, String.valueOf(chars));
    }

    @Test
    public void testGetSubstringChars() {
        var s = "Hello World!";
        char[] chars = {'?', 0, 0, 0, 0, 0, '?'};

        s.getChars(6, 11, chars, 1);
        Assertions.assertEquals("?World?", String.valueOf(chars));
    }

    @Test
    public void testRegionMatchesOK() {
        Assertions.assertTrue("Hello World!".regionMatches(6, "My World is good!", 3, 5));
    }

    @Test
    public void testRegionMatchesFalse() {
        Assertions.assertFalse("Hello World!".regionMatches(6, "My World is good!", 0, 5));
    }

    @Test
    public void testCompare() throws Exception {
        String one = "test";
        String two = "test";
        System.out.println(one.equals(two));
    }

    @Test
    public void testCompareTo() {
        Assertions.assertTrue("aaa".compareTo("bb") < 0);
        Assertions.assertEquals(0, "aa".compareTo("aa"));
        Assertions.assertTrue("bb".compareTo("aa") > 0);
    }

    @Test
    public void testReplaceChars() {
        Assertions.assertEquals("abc".replace('b', '-'), "a-c");
    }

    @Test
    public void testReplaceCharSequence() {
        Assertions.assertEquals("abcdef".replace("cde", "kk"), "abkkf");
    }

    @Test
    public void testJoin() {
        Assertions.assertEquals("one-two-ten", String.join("-", "one", "two", "ten"));
    }

    @Test
    public void testJoinIterable() {
        var strings = Arrays.asList("one", "two", "ten");
        Assertions.assertEquals("one-two-ten", String.join("-", strings));
    }

    @Test
    public void testIsBlank() {
        // for empty string
        Assertions.assertTrue("".isBlank());
        // for the string full of spaces
        Assertions.assertTrue("  ".isBlank());
    }

    @Test
    public void testLines() {
        var s = new Formatter().format("Hello%nWorld!").toString();

        Stream<String> stringStream = s.lines();
        var list = stringStream.toList();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("Hello", list.get(0));
        Assertions.assertEquals("World!", list.get(1));
    }

    @Test
    public void testRepeat() {
        var s = "foo".repeat(3);
        Assertions.assertEquals("foofoofoo", s);
    }

    @Test
    public void testStrip() {
        var s = "   Hello World!  ".strip();
        Assertions.assertEquals("Hello World!", s);
    }

    @Test
    public void testStripLeading() {
        var s = "  Hello World!  ".stripLeading();
        Assertions.assertEquals("Hello World!  ", s);
    }

    @Test
    public void testStripTrailing() {
        var s = "  Hello World!  ".stripTrailing();
        Assertions.assertEquals("  Hello World!", s);
    }

    @Test
    public void testStripIndent() throws Exception {
        var s = """
                Hello
                World!
                """;

        Assertions.assertEquals("Hello\nWorld!\n", s.stripIndent());
    }

    @Test
    public void testFormatted() throws Exception {
        Assertions.assertEquals("Hello John!", "Hello %s!".formatted("John"));
    }
}
