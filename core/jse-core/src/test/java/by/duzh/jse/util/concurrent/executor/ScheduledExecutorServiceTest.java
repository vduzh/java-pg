package by.duzh.jse.util.concurrent.executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Disabled
public class ScheduledExecutorServiceTest {
    private ScheduledExecutorService executorService;
    private int[] count;

    @BeforeEach
    public void init() {
        executorService = Executors.newScheduledThreadPool(1);
        count = new int[]{0};
    }

    private Runnable getCommand(int[] count, int delay) {
        return () -> {
            //System.out.println(Thread.currentThread().getName() + " starts");
            try {
                Thread.sleep(delay);
                synchronized (count) {
                    count[0] += 1;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(Thread.currentThread().getName() + " ends");
        };
    }

    @Test
    public void testSchedule() throws Exception {
        for (int i = 0; i < 5; i++) {
            executorService.schedule(getCommand(count, 500), 3, TimeUnit.SECONDS);
        }
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertEquals(5, count[0]);
    }

    @Test
    public void testScheduleWithFixedRate() throws Exception {
        executorService.scheduleAtFixedRate(getCommand(count, 500), 2, 1, TimeUnit.SECONDS);
        Thread.sleep(4200);
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertEquals(3, count[0]);
    }

    @Test
    public void testScheduleWithFixedRateLongCommand() throws Exception {
        executorService.scheduleAtFixedRate(getCommand(count, 1500), 2, 1, TimeUnit.SECONDS);
        Thread.sleep(4200);
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertEquals(2, count[0]);
    }

    @Test
    public void testScheduleWithFixedDelay() throws Exception {
        executorService.scheduleWithFixedDelay(getCommand(count, 500), 2, 1, TimeUnit.SECONDS);
        Thread.sleep(4200);
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertEquals(2, count[0]);
    }

    @Test
    public void testScheduleWithFixedDelayWithLongCommand() throws Exception {
        executorService.scheduleWithFixedDelay(getCommand(count, 2000), 2, 1, TimeUnit.SECONDS);
        Thread.sleep(4200);
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertEquals(1, count[0]);
    }
}
