package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.*;

public class CallableTest {
    Callable<Integer> task;

    ExecutorService executor;

    @BeforeEach
    public void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    public void destroy() {
        // first stop taking new tasks
        executor.shutdown();
        try {
            // then wait up to a specified period of time for all tasks to be completed
            if (!executor.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                // If that time expires, the execution is stopped immediately
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    @Test
    public void testCall() throws Exception {
        // implement callable
        task = () -> {
            TimeUnit.SECONDS.sleep(2);
            return 5;
        };

        // submit callable
        Future<Integer> future = executor.submit(task);

        Assertions.assertEquals(5, future.get().intValue());
    }

    @Test
    public void testCallException() throws Exception {
        task = () -> {
            throw new FileNotFoundException();
        };

        Future<Integer> future = executor.submit(task);

        try {
            future.get();
        } catch (Exception e) {
            // thrown as the call method generates the FileNotFoundException
            Assertions.assertTrue(e.getCause() instanceof FileNotFoundException);
        }
    }
}