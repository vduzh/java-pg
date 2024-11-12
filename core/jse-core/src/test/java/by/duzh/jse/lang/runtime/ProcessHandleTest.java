package by.duzh.jse.lang.runtime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// Jdk9
public class ProcessHandleTest {
    private ProcessHandle processHandle;

    @Before
    public void init() throws Exception {
        // JVM process
        processHandle = ProcessHandle.current();
    }

    @Test
    public void testCreate() throws Exception {
        // handle of the current process
        processHandle = ProcessHandle.current();
        Assert.assertNotNull(processHandle);

        // handle of the process run by JVM
        processHandle = new ProcessBuilder()
                .command("java", "--help")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .start()
                .toHandle();
        Assert.assertNotNull(processHandle);

        // handle of any process
        Optional<ProcessHandle> opt = ProcessHandle.of(ProcessHandle.current().pid());
        Assert.assertTrue(opt.isPresent());
    }

    @Test
    public void testPid() throws Exception {
        long pid = processHandle.pid();
        Assert.assertNotEquals(0, pid);
    }

    @Test
    public void testInfo() throws Exception {
        ProcessHandle.Info info = processHandle.info();

        Optional<String[]> arguments = info.arguments();
        Assert.assertFalse(arguments.isPresent());

        Optional<String> command = info.command();
        Assert.assertTrue(command.isPresent());
        Assert.assertTrue(command.get().contains("java"));

        Optional<String> commandLine = info.commandLine();
        Assert.assertFalse(commandLine.isPresent()); // as arguments are empty

        Optional<Instant> instant = info.startInstant();
        Assert.assertTrue(instant.isPresent());

        Optional<Duration> totalCpuDuration = info.totalCpuDuration();
        Assert.assertTrue(totalCpuDuration.isPresent());

        Optional<String> user = info.user();
        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void testAllProcesses() throws Exception {
        long count = ProcessHandle.allProcesses().count();
        Assert.assertNotEquals(0, count);
    }

    @Test
    public void testParent() throws Exception {
        Optional<ProcessHandle> opt = processHandle.parent();
        Assert.assertTrue(opt.isPresent());
    }

    @Test
    public void testChildren() throws Exception {
        long count = processHandle.children().peek(System.out::println).count();
        Assert.assertNotEquals(0, count);
    }

    @Test
    public void testDescendants() throws Exception {
        long count = processHandle.descendants().peek(e -> System.out.println(e.pid())).count();
        System.out.println(count);
        Assert.assertNotEquals(0, count);
    }

    @Test(expected = IllegalStateException.class)
    public void testDestroy() throws Exception {
        boolean destroyed = processHandle.destroy();
        Assert.assertFalse(destroyed);
    }

    @Test(expected = IllegalStateException.class)
    public void testDestroyForcibly() throws Exception {
        boolean destroyed = processHandle.destroyForcibly();
        Assert.assertFalse(destroyed);
    }

    @Test
    public void testAlive() throws Exception {
        Assert.assertTrue(processHandle.isAlive());
    }

    @Test
    public void testOnExit() throws Exception {
        boolean[] res = {false};

        Process process = new ProcessBuilder()
                .command("java", "--help")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD) //must be to prevent blocking
                .start();

        processHandle = process.toHandle();

        CompletableFuture<ProcessHandle> future = processHandle.onExit();
        future.thenAccept(ph -> res[0] = true);

        // wait for the process termination
        future.get();

        Assert.assertFalse(process.isAlive());
        Assert.assertTrue(res[0]);
    }

    @Test
    public void testSupportsNormalTermination() throws Exception {
        boolean res = processHandle.supportsNormalTermination();
        Assert.assertFalse(res);
    }

    @Test
    public void testEquals() throws Exception {
        // TODO implement
        //processHandle

    }
}
