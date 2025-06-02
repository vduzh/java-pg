package by.duzh.jse.util.concurrent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.util.concurrent.TimeUnit.SECONDS;

@Disabled
public class FutureTaskTest {
    private FutureTask<Boolean> futureTask;
    private ExecutorService service;

    @BeforeEach
    public void init() {
        service = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    public void destroy() throws Exception {
        service.shutdown();
        service.awaitTermination(3, SECONDS);
    }

    @Test
    public void create() throws Exception {
        futureTask = new FutureTask<>(() -> true);
        //Future<Boolean> submit = service.submit(futureTask);

        //Assertions.assertTrue(submit.get());
    }
}
