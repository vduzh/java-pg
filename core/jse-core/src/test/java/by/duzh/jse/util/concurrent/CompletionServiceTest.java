package by.duzh.jse.util.concurrent;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Disabled
public class CompletionServiceTest {
    private ExecutorService executorService;

    private CompletionService<String> completionService;

    @BeforeEach
    public void init() throws Exception {
        executorService = Executors.newCachedThreadPool();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // first stop taking new tasks
        executorService.shutdown();
        try {
            // then wait up to a specified period of time for all tasks to be completed
            if (!executorService.awaitTermination(800, MILLISECONDS)) {
                // If that time expires, the execution is stopped immediately
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    @Test
    public void create() {
        completionService = new ExecutorCompletionService<>(executorService);
    }

    @Test
    public void testSubmit() throws Exception {
        completionService = new ExecutorCompletionService<>(executorService);

        // submit a task
        var future = completionService.submit(() -> "OK");
    }

    @Test
    public void testTake() throws Exception {
        completionService = new ExecutorCompletionService<>(executorService);

        // test data
        var data = Arrays.asList("OK", "True", "Done");

        // Batch submit of 3 tasks
        data.forEach(s -> completionService.submit(() -> s));

        // Get results
        for (int i = 0; i < data.size(); i++) {
            var future = completionService.take();
            Assertions.assertTrue(data.contains(future.get()));
        }
    }

    @Test
    public void testPoll() throws Exception {
        completionService = new ExecutorCompletionService<>(executorService);

        // completed task is not present
        Assertions.assertNull(completionService.poll());

        // completed task is not present
        var future1 = completionService.submit(() -> "OK");
        SECONDS.sleep(2);
        var future = completionService.poll();
        Assertions.assertNotNull(future);
        Assertions.assertEquals(future1, future);
        Assertions.assertEquals("OK", future.get());
    }

    @Test
    public void testPollWithTimeout() throws Exception {
        completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> {
            MILLISECONDS.sleep(300);
            return "A";
        });
        completionService.submit(() -> {
            SECONDS.sleep(1);
            return "B";
        });
        completionService.submit(() -> {
            SECONDS.sleep(5);
            return "B";
        });

        var futureA = completionService.poll(1, SECONDS);
        var futureB = completionService.poll(1, SECONDS);
        var futureC = completionService.poll(1, SECONDS);

        Assertions.assertNotNull(futureA);
        Assertions.assertNotNull(futureB);
        Assertions.assertNull(futureC);
    }

    @Test
    public void travelReservations() throws Exception {

        completionService = new ExecutorCompletionService<>(executorService);

        // fetch data in parallel from multiple data sources
        var a = completionService.submit(() -> {
            MILLISECONDS.sleep(300);
            return "A";
        });
        var b =completionService.submit(() -> {
            SECONDS.sleep(1);
            return "B";
        });
        var c = completionService.submit(() -> {
            SECONDS.sleep(5);
            return "B";
        });

        // Wait for some time
        SECONDS.sleep(3);

        // get either result or null
        var futureA = completionService.poll();
        var futureB = completionService.poll();
        var futureC = completionService.poll();

        // Validate the result
        Assertions.assertNotNull(futureA);
        Assertions.assertEquals(a, futureA);

        Assertions.assertNotNull(futureB);
        Assertions.assertEquals(b, futureB);

        Assertions.assertNull(futureC);
        c.cancel(true);
    }
}
