package by.duzh.jse.util.concurrent.executor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ExecutorServiceTest {
    private ExecutorService executorService;

    @Before
    public void init() {
        executorService = Executors.newFixedThreadPool(5); // via a factory method
    }

    @Test
    public void testLifecycle() throws Exception {
        /*State: running*/
        Assert.assertFalse(executorService.isTerminated());

        executorService.execute(() -> {
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        });

        /*State: shutting down*/
        // send a shutdown request
        executorService.shutdown();
        // Tasks are still working...
        Assert.assertFalse(executorService.isTerminated());
        Assert.assertTrue(executorService.isShutdown());

        /*State: terminated*/
        var isTerminated = executorService.awaitTermination(4, SECONDS);
        Assert.assertTrue(isTerminated);
        Assert.assertTrue(executorService.isTerminated());
    }

    @Test
    public void testSubmit() throws Exception {
        // submit a callable task
        Future<Boolean> f1 = executorService.submit(() -> {
            System.out.println("Callable task: working...");
            return true;
        });
        Assert.assertTrue(f1.get());

        // submit a runnable task
        Future<?> f2 = executorService.submit(() -> System.out.println("Runnable task: working..."));
        f2.get();

        // submit a runnable task with result
        Future<String> future = executorService.submit(() -> {
            System.out.println("Runnable task: starting...");
            try {
                SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Runnable task: interrupted!");
                currentThread().interrupt();
            }
            System.out.println("Runnable task: finished!");

        }, "TEST");
        Assert.assertEquals("TEST", future.get());

        executorService.shutdown();
    }

    @Test
    public void invokeAll() throws Exception {
        List<Callable<String>> tasks = Arrays.asList(
                () -> "Site 1: " + (ThreadLocalRandom.current().nextInt(10) + 1),
                () -> "Site 2: " + (ThreadLocalRandom.current().nextInt(5) + 1)
        );

        List<Future<String>> futures = executorService.invokeAll(tasks);
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        executorService.shutdown();
    }

    @Test
    public void invokeAllWithTermination() throws Exception {
        List<Callable<String>> tasks = Arrays.asList(
                () -> {
                    SECONDS.sleep(1);
                    return "Site 1: " + (ThreadLocalRandom.current().nextInt(10) + 1);
                },
                () -> {
                    SECONDS.sleep(2);
                    return "Site 2: " + (ThreadLocalRandom.current().nextInt(5) + 1);
                },
                () -> {
                    SECONDS.sleep(10);
                    return "Site 10: " + (ThreadLocalRandom.current().nextInt(7) + 1);
                }
        );

        List<Future<String>> futures = executorService.invokeAll(tasks, 4, SECONDS);

        for (Future<String> future : futures) {
            //tasks.iterator.next
            try {
                System.out.println(future.get());
            } catch (CancellationException e) {
                // handle cancellation
                e.printStackTrace();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    @Test
    public void testAwaitTermination() throws Exception {
        executorService.submit(() -> "Finished!");
        executorService.shutdown();

        // like join : wait for some time to finish execution of the commands
        Assert.assertTrue(executorService.awaitTermination(5, SECONDS));
    }

    @Test
    public void testAwaitTerminationWithTimeOut() throws Exception {
        executorService.submit(() -> {
            SECONDS.sleep(5);
            return "Finished!";
        });
        executorService.shutdown();

        // tasks are still working...
        Assert.assertFalse(executorService.awaitTermination(1, SECONDS));
    }

    @Test
    public void testShutdown() throws Exception {
        // Stop accepting new tasks and shut down after all running threads finish their current work
        executorService.shutdown();

/*
        executorService.submit(() -> {
            System.out.println(1);
        });
*/

    }

    @Test
    public void testShutdownNow() throws Exception {
        // Tries to destroy the ExecutorService immediately, but it doesn't guarantee that all the running threads will be stopped at the same time
        List<Runnable> notExecutedTasks = executorService.shutdownNow();
    }

    @Test
    public void testOracleRecommendedShutdown() throws Exception {
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
    public void testIsShutdown() throws Exception {
        executorService.submit(() -> {
            SECONDS.sleep(5);
            return "Finished!";
        });
        executorService.shutdown();

        Assert.assertTrue(executorService.isShutdown());
    }

    @Test
    public void testIsTerminated() throws Exception {
        executorService.submit(() -> {
            SECONDS.sleep(2);
            return "Finished!";
        });

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            MILLISECONDS.sleep(200);
        }
    }

    @Test
    public void testIsTerminatedFalse() throws Exception {
        executorService.submit(() -> {
            SECONDS.sleep(5);
            return "Finished!";
        });

        executorService.shutdown();
        executorService.awaitTermination(2, SECONDS);

        Assert.assertFalse(executorService.isTerminated());
    }

    static class ReservationTask implements Callable<String> {
        final private String name;
        final private int duration;

        public ReservationTask(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        @Override
        public String call() throws Exception {
            int remain = duration;

            while (remain-- > 0) {
                SECONDS.sleep(1);
            }

            return name + ": " + duration;
        }
    }

    @Test
    public void testReservation() throws Exception {

        List<ReservationTask> destinations = Arrays.asList(
                new ReservationTask("Booking", 1),
                new ReservationTask("Hotels.com", 2),
                new ReservationTask("Airbnb", 3),
                new ReservationTask("Yandex", 6));

        ExecutorService executorService = Executors.newCachedThreadPool();

        final var futures = executorService.invokeAll(destinations, 4, SECONDS);

        for (int i = 0; i < futures.toArray().length; i++) {
            Future<String> f = futures.get(i);
            try {
                var result = f.get();
                System.out.println(result);
            } catch (Exception e) {
                System.out.printf("Can't get data from %s! %n", destinations.get(i).getName());
            }
        }

        executorService.shutdown();
    }
}