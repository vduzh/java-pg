package by.duzh.jse.util.concurrent.executor;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {
    private ExecutorService executorService;

    @Test
    public void testNewFixedThreadPool() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @Test
    public void testNewWorkStealingPool() {
        //Executors.newWorkStealingPool()
        //Executors.newWorkStealingPool(2)

        throw new RuntimeException("testNewWorkStealingPool");
    }

    @Test
    public void testNewSingleThreadExecutor() {
        // Thread pool with 1 thread
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void testNewCachedThreadPool() {
        executorService = Executors.newCachedThreadPool();
    }

    @Test
    public void testNewSingleThreadScheduledExecutor() {
        throw new RuntimeException("newSingleThreadScheduledExecutor");
    }

    @Test
    public void testNewScheduledThreadPool() {
        executorService = Executors.newScheduledThreadPool(1);
    }

    @Test
    public void testUnconfigurableExecutorService() {
        throw new RuntimeException("unconfigurableExecutorService");
    }

    @Test
    public void testDefaultThreadFactory() {
        throw new RuntimeException("defaultThreadFactory");
    }

    @Test
    public void testPrivilegedThreadFactory() {
        throw new RuntimeException("privilegedThreadFactory");
    }

    @Test
    public void testCallable() {
        throw new RuntimeException("callable");
    }

    @Test
    public void testPrivilegedCallable() {
        throw new RuntimeException("privilegedCallable");
    }

    @Test
    public void testPrivilegedCallableUsingCurrentClassLoader() {
        throw new RuntimeException("privilegedCallableUsingCurrentClassLoader");
    }

    @After
    public void tearDown() throws Exception {
        if (executorService != null)
            executorService.shutdown();
    }
}
