package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {
    private static final Logger logger = Logger.getLogger(ExecutorsTest.class.getName());

    private ExecutorService executorService;

    @Test
    public void testNewFixedThreadPool() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @Test
    public void testNewWorkStealingPool() {
        logger.warning("WARNING!!! Test is not implemented yet!");
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
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testNewScheduledThreadPool() {
        executorService = Executors.newScheduledThreadPool(1);
    }

    @Test
    public void testUnconfigurableExecutorService() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testDefaultThreadFactory() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testPrivilegedThreadFactory() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testCallable() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testPrivilegedCallable() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    public void testPrivilegedCallableUsingCurrentClassLoader() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (executorService != null)
            executorService.shutdown();
    }
}
