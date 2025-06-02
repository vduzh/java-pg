package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ThreadPoolExecutorTest {

    @Test
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
        Assertions.assertThrows(RejectedExecutionException.class, () -> {
            executor.execute(() -> {
            });
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
        Assertions.assertEquals(4, count.intValue());
    }

    @Test
    public void testSetRejectedExecutionHandlerWithDiscardOldestPolicy() throws Exception {
        var executor = new ThreadPoolExecutor(2, 2, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                count.addAndGet(taskId);
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

        // При использовании DiscardOldestPolicy, старейшая задача в очереди отбрасывается
        // и заменяется новой. В данном случае, задачи 1 и 2 выполняются в потоках,
        // задачи 3 и 4 находятся в очереди, а задача 5 заменяет задачу 3.
        // Итого: 1 + 2 + 4 + 5 = 12
        Assertions.assertEquals(12, count.intValue());
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