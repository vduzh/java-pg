package by.duzh.jse.lang.runtime;

import org.junit.Assert;
import org.junit.Test;

public class RuntimeTest {
    private final Runtime runtime = Runtime.getRuntime();

    @Test
    public void testGetRuntime() {
        Runtime runtime = Runtime.getRuntime();
    }

    @Test
    public void testAvailableProcessors() throws Exception {
        var num = runtime.availableProcessors();
        Assert.assertTrue(num > 0);
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

        Assert.assertTrue(freeMemory > 0);
        Assert.assertTrue(maxMemory > 0);
        Assert.assertTrue(totalMemory > 0);
    }

    @Test
    public void testGc() throws Exception {
        runtime.gc();
    }

    @Test
    public void testRunFinalization() throws Exception {
        runtime.runFinalization();
    }

    @Test
    public void testJDK10RuntimeVersion() throws Exception {
        Runtime.Version version = Runtime.version();

        Assert.assertTrue(version.feature() >= 10);
        Assert.assertTrue(version.interim() >= 0);
        Assert.assertTrue(version.patch() >= 0);
        Assert.assertTrue(version.update() >= 0);
    }
}
