package by.duzh.jse.util.concurrent.executor;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ThreadPoolExecutorTest {

    @Test(expected = RejectedExecutionException.class)
    public void testSetRejectedDefaultAbortExecutionHandler() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));

        // - 2 tasks are being processed + tasks are waiting in the queue
        for (int i = 0; i < 4; i++) {
            // exception will be thrown here fot the 5-th task
            executor.execute(() -> {
                try {
                    SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            });
        }

        // - 1 task can not be placed to the queue as it is full -> RejectedExecutionException
        executor.execute(() -> {
        });

        executor.shutdown();
    }

    @Test
    public void testSetRejectedExecutionHandlerWithDiscardPolicy() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            // exception will be thrown here fot the 5-th task
            executor.execute(() -> {
                count.addAndGet(1);
                try {
                    SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            });
        }

        SECONDS.sleep(6);

        executor.shutdown();
        executor.awaitTermination(5, SECONDS);

        // the 5-th task has been discharged
        Assert.assertEquals(4, count.intValue());
    }

    @Test
    public void testSetRejectedExecutionHandlerWithDiscardOldestPolicy() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 1; i <= 5; i++) {
            int j = i;
            // exception will be thrown here fot the 5-th task
            executor.execute(() -> {
                count.addAndGet(j);
                try {
                    SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            });
        }

        SECONDS.sleep(6);

        executor.shutdown();
        executor.awaitTermination(5, SECONDS);

        Assert.assertEquals(9, count.intValue());
    }

    @Test
    public void testBeforeExecute() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0, MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                // add custom behaviour
                System.out.println("beforeExecute is working");
            }
        };

        executor.execute(() -> {
        });

        executor.shutdown();
        executor.awaitTermination(5, SECONDS);
    }

    @Test
    public void testAfterExecute() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0, MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                // add custom behaviour
                System.out.println("afterExecute is working");

                super.afterExecute(r, t);
            }
        };

        executor.execute(() -> {
        });

        executor.shutdown();
        executor.awaitTermination(5, SECONDS);
    }

    @Test
    public void testTerminated() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0, MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void terminated() {
                // add custom behaviour
                System.out.println("terminated is working");

                super.terminated();
            }
        };

        executor.execute(() -> {
        });

        executor.shutdown();
        executor.awaitTermination(5, SECONDS);
    }
}