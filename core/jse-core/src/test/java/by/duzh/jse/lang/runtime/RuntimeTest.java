package by.duzh.jse.lang.runtime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RuntimeTest {
    private final Runtime runtime = Runtime.getRuntime();

    @Test
    public void testGetRuntime() {
        Runtime runtime = Runtime.getRuntime();
    }

    @Test
    public void testAvailableProcessors() throws Exception {
        var num = runtime.availableProcessors();
        Assertions.assertTrue(num > 0);
    }

    @Test
    public void testAddShutdownHook() throws Exception {
        runtime.addShutdownHook(new Thread(() -> System.out.println("addShutdownHook is working...")));
    }

    @Test
    public void testExec() throws Exception {
        var process = runtime.exec("java");
    }

    @Test
    public void testMemory() throws Exception {
        var freeMemory = runtime.freeMemory();
        var maxMemory = runtime.maxMemory();
        var totalMemory = runtime.totalMemory();

        Assertions.assertTrue(freeMemory > 0);
        Assertions.assertTrue(maxMemory > 0);
        Assertions.assertTrue(totalMemory > 0);
    }

    @Test
    public void testGc() throws Exception {
        runtime.gc();
    }

    @Test
    public void testRunFinalization() throws Exception {
        // runFinalization() is deprecated since Java 18
        // Using gc() instead as a similar cleanup operation
        runtime.gc();
        System.gc(); // Alternative way to suggest garbage collection
    }

    @Test
    public void testJDK10RuntimeVersion() throws Exception {
        Runtime.Version version = Runtime.version();

        Assertions.assertTrue(version.feature() >= 10);
        Assertions.assertTrue(version.interim() >= 0);
        Assertions.assertTrue(version.patch() >= 0);
        Assertions.assertTrue(version.update() >= 0);
    }
}
