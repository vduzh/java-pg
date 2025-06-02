package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ExecutorTest {
    @Test
    public void testExecute() throws Exception {
        // Create a task firstly
        final Runnable task = () -> {
            System.out.println("Task: starting...");

            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Task: the thread has been interrupted!");
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("Task: finished!");
        };

        // Secondly, create an executor and submit the task
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(task);

        // Shut the executor down
        ((ExecutorService) executor).shutdown();
        // wait for the shutting down
        ((ExecutorService) executor).awaitTermination(3, SECONDS);

    }
}