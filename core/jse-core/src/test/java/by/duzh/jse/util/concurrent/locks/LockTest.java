package by.duzh.jse.util.concurrent.locks;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Counter {
    static int value = 0;
}

public class LockTest {

    @Test
    public void testLockAndUnlock() throws Exception {
        CountDownLatch latch = new CountDownLatch(20);

        Lock lock = new ReentrantLock();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int j = 0; j < 4; j++) {
            executor.execute(() -> {
                for (int i = 0; i < 5; i++) {
                    lock.lock();
                    try {
                        // do some job
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Counter.value++;
                    } finally {
                        lock.unlock();
                    }
                    latch.countDown();
                }
            });
        }

        latch.await();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        Assert.assertEquals(20, Counter.value);
    }

    @Test
    public void testLockInterruptibly() throws Exception {

    }

    @Test
    public void testTryLock() throws Exception {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);

        Lock lock = new ReentrantLock();

        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            try {
                s1.acquire();

                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } finally {
                    lock.unlock();
                }
                latch.countDown();
                s2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                s2.acquire();

                while (!lock.tryLock()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                lock.unlock();

                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        latch.await(10, TimeUnit.SECONDS);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}