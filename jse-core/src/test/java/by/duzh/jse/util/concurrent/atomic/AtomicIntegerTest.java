package by.duzh.jse.util.concurrent.atomic;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Write the rest tests
public class AtomicIntegerTest {

    @Test
    public void testIncrementAndGet() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 2; i++) {
            executor.submit(() -> {
                for (int j = 1; j <= 100; j++) {
                    int res = counter.incrementAndGet();
                }
            });
        }
        executor.shutdown();

        executor.awaitTermination(10, TimeUnit.SECONDS);
        Assert.assertEquals(200, counter.get());
    }

    @Test
    public void testGetAndIncrement() throws Exception {
        AtomicInteger counter = new AtomicInteger(10);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> result = executor.submit(counter::getAndIncrement);
        executor.shutdown();

        Assert.assertEquals(10, result.get().intValue()); // synchronized call
        Assert.assertEquals(11, counter.get());
    }

    @Test
    public void testAddAndGet() throws Exception {
        AtomicInteger counter = new AtomicInteger(10);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> result = executor.submit(() -> counter.addAndGet(5));
        executor.shutdown();

        Assert.assertEquals(15, result.get().intValue()); // synchronized call
        Assert.assertEquals(15, counter.get());
    }

    @Test
    public void testGetAndAdd() throws Exception {
        AtomicInteger counter = new AtomicInteger(10);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> result = executor.submit(() -> counter.getAndAdd(5));
        executor.shutdown();

        Assert.assertEquals(10, result.get().intValue()); // synchronized call
        Assert.assertEquals(15, counter.get());
    }
}
