package by.duzh.jse.util.concurrent.forkjoin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

@Disabled
public class ForkJoinPoolTest {

    static class FooForkJoinTask extends RecursiveTask<String> {
        @Override
        protected String compute() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("FooForkJoinTask: compute is working...");
            return "foo";
        }
    };

    @Test
    public void testCreateWithConstructor() {
        // with the parallelism equals to the number of processors
        ForkJoinPool pool = new ForkJoinPool();

        final int parallelism = 2; // number of processors to use
        pool = new ForkJoinPool(parallelism);
    }

    @Test
    public void testCreateWithCommonPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
    }

    @Test
    public void testGetParallelism() {
        var pool = new ForkJoinPool(2);

        int parallelism = pool.getParallelism();

        Assertions.assertEquals(2, parallelism);
    }

    @Test
    public void testGetCommonPoolParallelism() {
        int parallelism = ForkJoinPool.getCommonPoolParallelism();
    }

    @Test
    public void testInvoke() {
        // Sync: perform the task and wait for the result
        String res = ForkJoinPool.commonPool().invoke(new FooForkJoinTask());

        Assertions.assertEquals("foo", res);
    }

    @Test
    public void testExecute() throws Exception {
        ForkJoinTask<?> task = new FooForkJoinTask();

        // async: do not wait for the end of the task
        ForkJoinPool.commonPool().execute(task);

        // wait for the task to finish
        TimeUnit.SECONDS.sleep(5);

        // async: do not wait for the end of the task
        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable is working...");
        };
        ForkJoinPool.commonPool().execute(runnable);

        // wait for the task to finish
        TimeUnit.SECONDS.sleep(5);
    }
}
