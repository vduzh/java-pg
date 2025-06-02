package by.duzh.jse.lang.runtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.util.UUID;

public class ProcessBuilderTest {
    private ProcessBuilder builder;

    @BeforeEach
    public void init() {
        builder = new ProcessBuilder().command("java", "--help");
    }

    @Test
    public void testCreate() throws Exception {
        builder = new ProcessBuilder("java");

        // program and its argument
        builder = new ProcessBuilder().command("java", "--help");
    }

    @Test
    public void testStart() throws Exception {
        builder.redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
    }

    @Test
    public void testRedirectOutput() throws Exception {
        String name = System.getProperty("java.io.tmpdir") + UUID.randomUUID() + ".txt";
        Process process = builder.redirectOutput(new File(name)).start();

        process.waitFor();

        Assertions.assertTrue(new File(name).length() > 0);
    }

    @Test
    public void testInheritIO() throws Exception {
        builder.inheritIO();
    }
}
