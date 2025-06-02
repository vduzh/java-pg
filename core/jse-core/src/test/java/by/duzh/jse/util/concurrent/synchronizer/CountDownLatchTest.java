package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CountDownLatchTest {

    private void doSomeWork() {
        var name = Thread.currentThread().getName();

        System.out.printf("%s: starting a job...%n", name);
        try {
            SECONDS.sleep(ThreadLocalRandom.current().nextInt(8) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: finished the job!%n", name);
    }

    @Test
    public void basic() throws Exception {
        final int SIZE = 5;

        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            IntStream.range(0, SIZE).forEach(id -> executor.submit(() -> {
                doSomeWork();
                countDownLatch.countDown();
            }));

            // Wait until all the threads complete their jobs
            countDownLatch.await();
        } finally {
            executor.shutdown();
        }
    }

    @Test
    public void fireTasksOnConditionMeet() throws Exception {
        // synchronizer
        CountDownLatch startLatch = new CountDownLatch(3);
        CountDownLatch collectLatch = new CountDownLatch(3);

        String[] names = {"John", "Jack", "Ann", "David", "Helen"};

        ExecutorService executor = Executors.newFixedThreadPool(names.length);

        // waiting tasks a below
        List<Future<?>> futures = Arrays.stream(names).map(name -> (Future<?>) executor.submit(() -> {
            System.out.printf("%s is waiting in the queue...%n", name);
            try {
                // wait for a condition
                startLatch.await();

                // FIRE!!!
                System.out.printf("%s started.%n", name);
                SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
                System.out.printf("%s's finished!%n", name);

                // Mark that the task has finished its job
                collectLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).collect(Collectors.toList());

        // Count down Start latch
        for (int i = 1; i <= 3; i++) {
            SECONDS.sleep(1);
            startLatch.countDown();
        }
        // The tasks get fired..

        // Wait the task to finish
        collectLatch.await();

        // Wait the task to finish
        executor.shutdown();
    }

    @Test
    public void splitterGather() throws Exception {
        int SIZE = 10;
        CountDownLatch latch = new CountDownLatch(SIZE);

        ExecutorService executor = Executors.newFixedThreadPool(SIZE);

        List<Future<String>> futures = new ArrayList<>();

        // Split work in several threads
        for (int i = 0; i < SIZE; i++) {
            int siteIndex = i;
            futures.add(executor.submit(() -> {
                try {
                    System.out.printf("Reading data fom site #%d...%n", siteIndex);
                    SECONDS.sleep(ThreadLocalRandom.current().nextInt(20) + 1);
                    System.out.printf("Got data fom site #%d!%n", siteIndex);

                    return "Data from site #" + siteIndex;
                } finally {
                    latch.countDown();
                }
            }));
        }

        // wait for results
        //latch.await();
        latch.await(10, SECONDS);

        // Gather all the results
        System.out.println("Result:");
        for (Future<String> future : futures) {
            if (!future.isDone()) {
                future.cancel(true);
                continue;
            }
            System.out.println(future.get());
        }
    }
}