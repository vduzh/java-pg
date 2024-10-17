package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CyclicBarrierTest {
    private CyclicBarrier barrier;

    @Test
    public void testWithoutBarrierAction() throws Exception {
        int parties = 3;

        barrier = new CyclicBarrier(parties);

        ExecutorService executor = Executors.newFixedThreadPool(parties);

        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " starts");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " has reached the point");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " continues his job");
                return true; // fake to use Callable :-)
            });
        }
        executor.shutdown();

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testWithBarrierAction() throws Exception {
        int parties = 3;

        barrier = new CyclicBarrier(parties, () -> {
            // TODO: this action gets called after the test has finished. use join????
            System.out.println("BarrierAction is working...");
        });

        ExecutorService executor = Executors.newFixedThreadPool(parties);

        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " starts");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " has reached the point");
                barrier.await();
                return true; // fake to use Callable :-)
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void splitterGather() throws Exception {
        int SIZE = 3;
        var res = new LinkedList<String>();

        final Runnable action = () -> {
            System.out.println("Result:");
            res.forEach(System.out::println);
        };
        CyclicBarrier barrier = new CyclicBarrier(SIZE, action);

        // Split work in several threads
        IntStream.range(0, 3).forEach(i ->
                new Thread(() -> {
                    try {
                        // to some work
                        System.out.printf("Reading data fom site #%d...%n", i);
                        var s = "Data #" + i;
                        Thread.sleep((i + 1) * 1000L);
                        System.out.printf("Got data fom site #%d!%n", i);

                        synchronized (res) {
                            res.add(s);
                        }
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start());

        // TODO: how to wait for the end of action object
        Thread.sleep(5000);
    }
}
