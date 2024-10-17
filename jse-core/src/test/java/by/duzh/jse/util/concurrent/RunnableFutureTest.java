package by.duzh.jse.util.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class RunnableFutureTest {
    interface CancellableTask<T> extends Callable<T> {
        void cancel();

        RunnableFuture<T> newTask();
    }

    static public class CancellingExecutor extends ThreadPoolExecutor {
        public CancellingExecutor(int nThreads) {
            super(nThreads, nThreads, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            if (callable instanceof CancellableTask) {
                return ((CancellableTask<T>) callable).newTask();
            }
            return super.newTaskFor(callable);
        }
    }

    static public abstract class AbstractCancellableTask<T> implements CancellableTask<T> {
        protected boolean isInterrupted;

        public synchronized void cancel() {
            isInterrupted = true;
        }

        @Override
        public RunnableFuture<T> newTask() {
            return new FutureTask<T>(this) {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    // do custom interruption
                    AbstractCancellableTask.this.cancel();
                    // call super cancel
                    return super.cancel(mayInterruptIfRunning);
                }
            };
        }
    }
    @Test
    public void testCancelTask() throws Exception {
        // create a task
        var task = new AbstractCancellableTask<String>() {
            @Override
            public String call() throws Exception {
                int i = 0;
                while(i++ < 15) {
                    System.out.println("Task: " + i);
                    if (this.isInterrupted) {
                        System.out.println("Task: has been interrupted!");
                        return null;
                    }
                    MILLISECONDS.sleep(300);
                }
                return "OK";
            }
        };

        var executorService = new CancellingExecutor(3);
        var future = executorService.submit(task);

        SECONDS.sleep(1);

        future.cancel(true);

        executorService.shutdown();
        executorService.awaitTermination(5, SECONDS);

    }
}
