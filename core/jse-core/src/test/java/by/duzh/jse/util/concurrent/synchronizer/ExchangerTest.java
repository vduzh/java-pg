package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    private Runnable getTask(String name, Exchanger<Integer> exchanger, List<Integer> numbers, long timeOut) {
        return () -> {
            numbers.forEach(i -> {
                try {
                    // Blocked until a counterpart thread replies.
                    int reply = exchanger.exchange(i);
                    if (i > reply) {
                        System.out.printf("%s has WON%n", name);
                    }
                    Thread.sleep(timeOut);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        };
    }

    @Test
    public void testExchange() throws Exception {
        // synchronizer
        Exchanger<Integer> exchanger = new Exchanger<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(getTask("David", exchanger, Arrays.asList(1, 5, 8), 1000));
        executor.submit(getTask("Ann", exchanger, Arrays.asList(5, 1, 8), 500));

        executor.shutdown();

        executor.awaitTermination(10, TimeUnit.SECONDS);
        //System.out.println("END");
    }

    @Test
    public void testCurrencyExchange() throws Exception {
        Exchanger<String> exchanger = new Exchanger<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            Thread.sleep(1000);
            String res = exchanger.exchange("10:USD");
            //System.out.println("1: 10:USD -> " + res);
            return true; // fake to use Callable
        });
        executor.submit(() -> {
            Thread.sleep(2000);
            String res = exchanger.exchange("26:BYN");
            //System.out.println("2: 26:BYN -> " + res);
            return true; // fake to use Callable
        });
        executor.shutdown();

        executor.awaitTermination(10, TimeUnit.SECONDS);
        //System.out.println("END");
    }


}
