package by.duzh.jse.util.concurrent.forkjoin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

// TODO: implement the rest methods
public class ForkJoinTaskTest {
    ForkJoinTask<String> task;

    private ForkJoinTask<String> getTask(String value) {
        return new RecursiveTask<String>() {

            @Override
            protected String compute() {
                if ("CANCEL".equals(value)) {
                    cancel(true);
                    return null;
                }

                if ("EXCEPTION".equals(value)) {
                    throw new RuntimeException("EXCEPTION");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return value;
            }
        };
    }

    @Before
    public void init() {
        task = getTask("OK!");
    }

    @Test
    public void testFork() {
        // Run the task asynchronously
        // Places task to the local queue and allows another to steal this task
        ForkJoinTask<String> task1 = task.fork();

        Assert.assertEquals(task, task1);
    }

    @Test
    public void testJoin() {
        task.fork();

        // Wait for the result
        String res = task.join();

        Assert.assertEquals("OK!", res);
    }

    @Test
    public void testIsDone() {
        task.fork();
        Assert.assertFalse(task.isDone());

        task.join();
        Assert.assertTrue(task.isDone());
    }

    @Test
    public void testInvoke() {
        // Run fork and join
        String res = task.invoke();

        Assert.assertEquals("OK!", res);
    }

    @Test
    public void testForkAndJoinAndInvoke() {
        ForkJoinTask<String> task1 = getTask("Done!");

        // run task1 asynchronously
        task1.fork();

        // wait for the results of both tasks
        String res = task.invoke() + task1.join();

        Assert.assertEquals("OK!Done!", res);
    }

    @Test
    public void testInvokeAll() throws Exception {
        ForkJoinTask<String> task1 = getTask("SECOND");

        // Run the tasks synchronously and wait for the result
        ForkJoinTask.invokeAll(task, task1);

        // Assert the results
        Assert.assertTrue(task.isDone());
        Assert.assertEquals("OK!", task.get());

        Assert.assertTrue(task1.isDone());
        Assert.assertEquals("SECOND", task1.get());
    }

    @Test
    public void testIsCompletedNormally() {
        task.invoke();
        Assert.assertTrue(task.isCompletedNormally());
        Assert.assertTrue(task.isDone());
    }

    @Test
    public void testIsCompletedAbnormally() {
        // failure via cancellation
        task = getTask("CANCEL");
        try {
            task.invoke();
        } catch (Exception ignored) {
        }
        Assert.assertTrue(task.isCompletedAbnormally());
        Assert.assertTrue(task.isDone());

        // failure via exception
        task = getTask("EXCEPTION");
        try {
            task.invoke();
        } catch (Exception ignored) {
        }
        Assert.assertTrue(task.isCompletedAbnormally());
        Assert.assertTrue(task.isDone());
    }


    @Test
    public void testReinitialize() throws Exception {
        task = getTask("OK");
        task.invoke();

        task.reinitialize();
        task.invoke();
    }

    @Test
    public void testInForkJoinPool() throws Exception {
        Assert.assertFalse(ForkJoinTask.inForkJoinPool());
    }

    @Test
    public void testAdapt() throws Exception {
        // Callable
        task = ForkJoinTask.adapt(()-> "OK");
        var res = task.invoke();

        Assert.assertEquals("OK", res);

        // Runnable
        var task1 = ForkJoinTask.adapt(()-> {});
        task1.invoke();

        // Runnable with result
        task = ForkJoinTask.adapt(()-> {}, "OK");
        res = task.invoke();

        Assert.assertEquals("OK", res);
    }

    @Test
    public void testGetQueuedTaskCount() throws Exception {


        System.out.println(ForkJoinTask.getQueuedTaskCount());
        //throw new RuntimeException("testGetQueuedTaskCount not implemented!");
    }
}