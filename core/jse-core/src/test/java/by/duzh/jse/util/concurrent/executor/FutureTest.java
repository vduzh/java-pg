package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * https://www.baeldung.com/java-future
 */
@Disabled
public class FutureTest {
    private Future<Boolean> future;
    private ExecutorService executor;

    @BeforeEach
    public void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    public void destroy() throws Exception {
        // first stop taking new tasks
        executor.shutdown();
        try {
            // then wait up to a specified period of time for all tasks to be completed
            if (!executor.awaitTermination(3000, MILLISECONDS)) {
                // If that time expires, the execution is stopped immediately
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private Callable<Boolean> getTask(int... delay) {
        return () -> {
            SECONDS.sleep(delay == null ? 5 : delay[0]);
            return true;
        };
    }

    @Test
    public void testGetWhichIsBlocked() throws Exception {
        future = executor.submit(getTask(2));

        // Blocked here for 2 seconds
        var b = future.get();

        // Assert
        Assertions.assertTrue(b);
    }

    @Test
    public void testGetWithTimeout() throws Exception {
        future = executor.submit(getTask(3));

        // TimeoutException will be thrown here
        Assertions.assertThrows(TimeoutException.class, () -> {
            future.get(1, SECONDS);
        });

        //TODO: if  TimeoutException has happened, call future.cancel(true)
    }

    @Test
    public void testGetWithException() throws Throwable {
        future = executor.submit(() -> {
            throw new FileNotFoundException();
        });

        Assertions.assertThrows(ExecutionException.class, () -> {
            future.get();
        });
    }

    @Test
    public void testGetWithCancelException() throws Exception {
        future = executor.submit(getTask());
        future.cancel(true);

        Assertions.assertThrows(CancellationException.class, () -> {
            future.get();
        });
    }

    @Test
    public void testIsDone() throws Exception {
        future = executor.submit(getTask());

        while (!future.isDone()) {
            System.out.printf("Task is being processed...%n");
            SECONDS.sleep(1);
        }
        System.out.println("Task is processed!");

        // Now we can get the result
        Assertions.assertTrue(future.get());
    }

    @Test
    public void testCancel() throws Exception {
        // Submit tasks
        future = executor.submit(() -> {
            SECONDS.sleep(2);
            return true;
        });

        // sleep for a while
        MILLISECONDS.sleep(300);

        // sends the cancel tasks request
        var res = future.cancel(true);

        // cancellation request delivered
        Assertions.assertTrue(res);
    }

    @Test
    public void testCancelFalse() throws Exception {
        future = executor.submit(getTask());

        var res = future.cancel(false);
        Assertions.assertTrue(res);
    }

    @Test
    public void testException() throws Exception {
        future = executor.submit(() -> {
            throw new FileNotFoundException();
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            Assertions.assertTrue(e.getCause() instanceof FileNotFoundException);
        }
    }
}
