package by.duzh.jse.io.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class PrintWriterTest {

    @Test
    public void testCreate() {
        // Without AutoFlashing
        PrintWriter printWriter = new PrintWriter(new CharArrayWriter());

        // With AutoFlashing
        printWriter = new PrintWriter(new CharArrayWriter(), true);

        // With Output Stream
        printWriter = new PrintWriter(new ByteArrayOutputStream());
    }

    @Test
    public void testPrintln() {
        String ls = System.lineSeparator();

        String s = "Test";
        CharArrayWriter writer = new CharArrayWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(s);
        printWriter.println(s);

        Assertions.assertEquals(s + ls + s + ls, new String(writer.toCharArray()));
    }

    @Test
    public void testPrint() {
        String s = "Test";
        CharArrayWriter writer = new CharArrayWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.print(s);
        printWriter.print(s);

        Assertions.assertEquals(s + s, new String(writer.toCharArray()));
    }

    @Test
    public void testPrintf() {
        CharArrayWriter writer = new CharArrayWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.printf("%s World!", "Hello");
        printWriter.printf("%s World!", "Bye");
        Assertions.assertEquals("Hello World!Bye World!", new String(writer.toCharArray()));
    }

    @Test
    public void testFormat() {
        CharArrayWriter writer = new CharArrayWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        PrintWriter printWriter1 = printWriter.format("%s World!", "Hello");
        Assertions.assertEquals(printWriter, printWriter1);
        Assertions.assertEquals("Hello World!", new String(writer.toCharArray()));
    }
}
