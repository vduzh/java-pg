package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;

// NOTE: Semaphore(1) == Lock!!!
public class SemaphoreTest {
    // synchronizer
    private Semaphore semaphore;

    @Before
    public void init() {
        semaphore = new Semaphore(1);
    }

    @Test
    public void testAcquireAndRelease() throws Exception {
        semaphore = new Semaphore(2);

        // Test of the main thread
        try {
            // acquire one permit
            semaphore.acquire();
            // Do some staff with shared resource
        } finally {
            // release one permit
            semaphore.release();
        }

        // Acquire 2 permits at once
        final int permits = 2;
        try {
            // acquire two permits
            semaphore.acquire(permits);
            // Do some staff with shared resource
        } finally {
            // release 2 permits
            semaphore.release(permits);
        }
    }

    @Test
    public void testCounter() throws Exception {
        class Counter {
            private int value;

            public Counter(int value) {
                this.value = value;
            }

            public void inc() {
                this.value++;
            }

            public void dec() {
                this.value--;
            }
        }

        // Shared resource
        var counter = new Counter(100);

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            // Inc executor
            Future<?> inc = executor.submit(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        semaphore.acquire();

                        // Do smth with shared resource
                        System.out.printf("IncrementING %d \n", counter.value);
                        counter.inc();
                        Thread.sleep(200);
                        System.out.printf("IncrementED to %d \n", counter.value);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release();
                    }

                    // just allow to switch context
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Dec executor
            Future<?> dec = executor.submit(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        semaphore.acquire();

                        // Do smth with shared resource
                        System.out.printf("DecrementING %d \n", counter.value);
                        counter.dec();
                        Thread.sleep(100);
                        System.out.printf("DecrementED to %d \n", counter.value);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release();
                    }

                    // just allow to switch context
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            while (!(inc.isDone() && dec.isDone())) {
                TimeUnit.SECONDS.sleep(1);
            }
        } finally {
            executor.shutdown();
        }

        Assert.assertEquals(100, counter.value);
    }

    @Test
    public void testAcquireAndReleaseTelephoneBooth() throws Exception {
        String[] names = {"John", "Jack", "Ann", "David", "Helen"};

        // synchronizer
        Semaphore telephoneBooth = new Semaphore(2);

        ExecutorService executor = Executors.newFixedThreadPool(names.length);
        try {

            Arrays.stream(names).forEach(name -> executor.submit(() -> {
                System.out.printf("%s is waiting for the Telephone booth...%n", name);
                try {
                    telephoneBooth.acquire();
                    System.out.printf("%s is calling%n", name);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.printf("%s's call ended%n", name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    telephoneBooth.release();
                }
            }));
        } finally {
            executor.shutdown();
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testQueue() throws Exception {
        class SemaphoredQueue {
            private Semaphore putSemaphore = new Semaphore(1);
            private Semaphore getSemaphore = new Semaphore(0);

            private int n;

            int get() {
                try {
                    getSemaphore.acquire();
                    //System.out.println(Thread.currentThread().getName() + " gets " + n);
                    int res = n;
                    Thread.sleep(300);
                    putSemaphore.release();
                    return res;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            void put(int value) {
                try {
                    putSemaphore.acquire();
                    //System.out.println(Thread.currentThread().getName() + " puts " + value);
                    n = value;
                    Thread.sleep(500);
                    getSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        SemaphoredQueue queue = new SemaphoredQueue();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(() -> {
            for (int i = 0; i < 5; i++) {
                queue.put(i * 10);
            }
        });

        executor.execute(() -> {
            for (int i = 0; i < 5; i++) {
                int res = queue.get();
            }
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
