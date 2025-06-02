package by.duzh.jse.lang.runtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class ProcessTest {
    private Process process;

    @BeforeEach
    public void init() throws Exception {
        process = new ProcessBuilder().command("java", "--help")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD) // must be
                .start();
    }

    @Test
    public void testWaitFor() throws Exception {
        process.waitFor();
    }

    @Test
    public void testWaitForWithTimeout() throws Exception {
        process.waitFor(2, TimeUnit.SECONDS);
    }

    @Test
    public void testDestroy() throws Exception {
        process.destroy();
    }

    @Test
    public void testDestroyForcibly() throws Exception {
        process.destroyForcibly();
    }

    @Test
    public void testExitValueZero() throws Exception {
        process.waitFor();
        Assertions.assertEquals(0, process.exitValue());
    }

    @Test
    public void testExitValueNotZero() throws Exception {
        process.destroyForcibly().waitFor();
        Assertions.assertNotEquals(0, process.exitValue());
    }

    @Test
    public void testIsAlive() throws Exception {
        Assertions.assertTrue(process.isAlive());
        process.waitFor();
        Assertions.assertFalse(process.isAlive());
    }

    @Test
    public void testJdk9ToHandle() throws Exception {
        ProcessHandle handle = process.toHandle();
        Assertions.assertNotNull(handle);
    }

    @Test
    public void testInputStream() throws Exception {
        process = new ProcessBuilder().command("java", "--help").start();
        BufferedInputStream in = new BufferedInputStream(process.getInputStream());

        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = in.read()) != -1) {
            sb.append((char) c);
        }

        process.waitFor();

        Assertions.assertEquals('U', sb.charAt(0));
    }

    @Test
    public void testOutputStream() throws Exception {
        OutputStream outputStream = process.getOutputStream();
        //outputStream.write("Hello".getBytes());
        Assertions.assertNotNull(outputStream);
    }
}
