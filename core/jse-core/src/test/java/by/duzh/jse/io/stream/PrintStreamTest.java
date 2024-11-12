package by.duzh.jse.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

// TODO: look into the source code of the PrintStream
public class PrintStreamTest {

    @Test
    public void testCreate() {
        // Without AutoFlashing
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // With AutoFlashing
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os, true)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrintln() {
        String ls = System.lineSeparator();

        String s = "Test";
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os)) {
            ps.println(s);
            ps.println(s);
            Assert.assertEquals(s + ls + s + ls, new String(os.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrint() {
        String s = "Test";
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os)) {
            ps.print(s);
            ps.print(s);
            Assert.assertEquals(s + s, new String(os.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrintf() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os)) {
            ps.printf("%s World!", "Hello");
            ps.printf("%s World!", "Bye");
            Assert.assertEquals("Hello World!Bye World!", new String(os.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
