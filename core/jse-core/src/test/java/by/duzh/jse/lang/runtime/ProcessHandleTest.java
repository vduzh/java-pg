package by.duzh.jse.lang.runtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// Jdk9
public class ProcessHandleTest {
    private ProcessHandle processHandle;

    @BeforeEach
    public void init() throws Exception {
        // JVM process
        processHandle = ProcessHandle.current();
    }

    @Test
    public void testCreate() throws Exception {
        // handle of the current process
        processHandle = ProcessHandle.current();
        Assertions.assertNotNull(processHandle);

        // handle of the process run by JVM
        processHandle = new ProcessBuilder()
                .command("java", "--help")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .start()
                .toHandle();
        Assertions.assertNotNull(processHandle);

        // handle of any process
        Optional<ProcessHandle> opt = ProcessHandle.of(ProcessHandle.current().pid());
        Assertions.assertTrue(opt.isPresent());
    }

    @Test
    public void testPid() throws Exception {
        long pid = processHandle.pid();
        Assertions.assertNotEquals(0, pid);
    }

    @Test
    public void testInfo() throws Exception {
        ProcessHandle.Info info = processHandle.info();

        Optional<String[]> arguments = info.arguments();
        Assertions.assertFalse(arguments.isPresent());

        Optional<String> command = info.command();
        Assertions.assertTrue(command.isPresent());
        Assertions.assertTrue(command.get().contains("java"));

        Optional<String> commandLine = info.commandLine();
        Assertions.assertFalse(commandLine.isPresent()); // as arguments are empty

        Optional<Instant> instant = info.startInstant();
        Assertions.assertTrue(instant.isPresent());

        Optional<Duration> totalCpuDuration = info.totalCpuDuration();
        Assertions.assertTrue(totalCpuDuration.isPresent());

        Optional<String> user = info.user();
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    public void testAllProcesses() throws Exception {
        long count = ProcessHandle.allProcesses().count();
        Assertions.assertNotEquals(0, count);
    }

    @Test
    public void testParent() throws Exception {
        Optional<ProcessHandle> opt = processHandle.parent();
        Assertions.assertTrue(opt.isPresent());
    }

    @Test
    public void testChildren() throws Exception {
        long count = processHandle.children().peek(System.out::println).count();
        Assertions.assertNotEquals(0, count);
    }

    @Test
    public void testDescendants() throws Exception {
        long count = processHandle.descendants().peek(e -> System.out.println(e.pid())).count();
        System.out.println(count);
        Assertions.assertNotEquals(0, count);
    }

    @Test
    public void testDestroy() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            boolean destroyed = processHandle.destroy();
            Assertions.assertFalse(destroyed);
        });
    }

    @Test
    public void testDestroyForcibly() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            boolean destroyed = processHandle.destroyForcibly();
            Assertions.assertFalse(destroyed);
        });
    }

    @Test
    public void testAlive() throws Exception {
        Assertions.assertTrue(processHandle.isAlive());
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

        Assertions.assertFalse(process.isAlive());
        Assertions.assertTrue(res[0]);
    }

    @Test
    public void testSupportsNormalTermination() throws Exception {
        boolean res = processHandle.supportsNormalTermination();
        Assertions.assertFalse(res);
    }

    @Test
    public void testEquals() throws Exception {
        // TODO implement
        //processHandle
    }
}
