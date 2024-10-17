package by.duzh.jse.util.concurrent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FutureTaskTest {
    private FutureTask<Boolean> futureTask;
    private ExecutorService service;

    @Before
    public void init() {
        service = Executors.newSingleThreadExecutor();
    }

    @After
    public void destroy() throws Exception {
        service.shutdown();
        service.awaitTermination(3, SECONDS);
    }

    @Test
    public void create() throws Exception {
        futureTask = new FutureTask<>(() -> true);
        //Future<Boolean> submit = service.submit(futureTask);

        //Assert.assertTrue(submit.get());
    }
}
