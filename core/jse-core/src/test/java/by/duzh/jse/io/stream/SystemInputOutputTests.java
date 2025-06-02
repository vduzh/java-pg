package by.duzh.jse.io.stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;

public class SystemInputOutputTests {

    @Test
    public void testReadCharactersSystemInOK() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("te".getBytes()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int value;
            while ((value = reader.read()) != -1) {
                Assertions.assertTrue('t' == value || 'e' == value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(in);
    }

    @Test
    public void testReadLineSystemInOK() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("first\nline\n".getBytes()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String s;
            while ((s = reader.readLine()) != null) {
                Assertions.assertTrue("first".equals(s) || "line".equals(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(in);
    }

    @Test
    public void testWriteToConsoleOk() {
        PrintStream writer = System.out;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        System.out.print("test");
        Assertions.assertEquals("test", os.toString());

        System.setOut(writer);
    }
}
