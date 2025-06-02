package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

//TODO: write the rest tests
@Disabled
public class PhaserTest {
    private Phaser phaser;
    private static final Logger logger = Logger.getLogger(PhaserTest.class.getName());

    @Test
    public void testCreate() throws Exception {
        // With no parties registered
        phaser = new Phaser();
        Assertions.assertEquals(0, phaser.getRegisteredParties());

        // With one party (the current thread) registered
        phaser = new Phaser(2);
        Assertions.assertEquals(2, phaser.getRegisteredParties());
    }

    @Test
    public void testGetPhase() throws Exception {
        phaser = new Phaser();
        Assertions.assertEquals(0, phaser.getPhase());
    }

    @Test
    public void testRegister() throws Exception {
        // Register 3 parties via a constructor
        phaser = new Phaser(3);

        // And 2 parties via the register method
        int phaseNumber = phaser.register();
        Thread thread = new Thread(() -> phaser.register());
        thread.start();
        // wait the thread to finish its work
        thread.join();

        Assertions.assertEquals(5, phaser.getRegisteredParties());
    }

    @Test
    public void testWait() throws Exception {
        phaser = new Phaser(2);
        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertEquals(0, phaser.getPhase());

        Thread thread = new Thread(() -> {
            simulateThreadWork(200);

            int phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(1, phase);
            Assertions.assertEquals(1, phaser.getPhase());

            phaser.arriveAndDeregister();
            latch.countDown();
        });
        thread.start();

        simulateThreadWork(500);
        int phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, phase);
        Assertions.assertEquals(1, phaser.getPhase());

        phaser.arriveAndDeregister();

        latch.await();
        thread.join();

        Assertions.assertEquals(0, phaser.getRegisteredParties());
        Assertions.assertTrue(phaser.getPhase() < 0);
        Assertions.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testArriveAndWaitAdvance() throws Exception {
        phaser = new Phaser(2);
        Assertions.assertEquals(0, phaser.getPhase());

        Thread thread = new Thread(() -> {
            simulateThreadWork(2000);

            int phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(1, phase);
            Assertions.assertEquals(1, phaser.getPhase());

            phaser.arriveAndDeregister();
        });
        thread.start();

        simulateThreadWork(300);

        int phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, phase);
        Assertions.assertEquals(1, phaser.getPhase());

        phaser.arriveAndDeregister();

        thread.join();

        Assertions.assertEquals(0, phaser.getRegisteredParties());
        Assertions.assertTrue(phaser.getPhase() < 0);
        Assertions.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testSeveralPhasesWithArriveAndAwaitAdvance() throws Exception {
        phaser = new Phaser(2);

        Thread one = new Thread(() -> {
            int phase;

            simulateThreadWork(100, "Phase1");
            phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(1, phase);

            simulateThreadWork(1500, "Phase2");
            phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(2, phase);

            phase = phaser.arriveAndDeregister();
            Assertions.assertEquals(2, phase);
        });
        one.start();

        int phase;

        simulateThreadWork(500, "Phase1");
        phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, phase);

        simulateThreadWork(1000, "Phase2");
        phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(2, phase);

        phase = phaser.arriveAndDeregister();
        Assertions.assertEquals(2, phase);

        Assertions.assertEquals(0, phaser.getRegisteredParties());
        Assertions.assertTrue(phaser.getPhase() < 0);
        Assertions.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testCarWash() throws Exception {
        int[] cars = {11, 22, 33, 44};
        phaser = new Phaser(3);

        class Washer extends Thread {
            private int[] cars;
            private Phaser phaser;
            private String name;

            public Washer(String name, int[] cars, Phaser phaser) {
                this.name = name;
                this.cars = cars;
                this.phaser = phaser;
            }

            @Override
            public void run() {
                Arrays.stream(cars).forEach(car -> {
                    int carNumber = phaser.getPhase() + 1;
                    simulateThreadWork(500, "car #" + carNumber + " " + name);
                    phaser.arriveAndAwaitAdvance();
                });
            }
        }

        Washer cleaner = new Washer("Cleaner", cars, phaser);
        Washer washer = new Washer("Washer", cars, phaser);
        Washer heater = new Washer("Heater", cars, phaser);

        cleaner.start();
        washer.start();
        heater.start();

        cleaner.join();
        washer.join();
        heater.join();
    }

    @Test
    public void testSeveralPhasesWithArrive() throws Exception {
        phaser = new Phaser(2);
        CountDownLatch latch = new CountDownLatch(1);

        Thread one = new Thread(() -> {
            int phase;

            simulateThreadWork(100, "Phase1");
            phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(1, phase);

            simulateThreadWork(1500, "Phase2");
            phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(2, phase);

            phase = phaser.arriveAndDeregister();
            Assertions.assertEquals(3, phase);
            latch.countDown();
        });
        one.start();

        int phase;

        simulateThreadWork(500, "Phase1");
        phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, phase);

        simulateThreadWork(1000, "Phase2");
        phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(2, phase);

        phase = phaser.arriveAndDeregister();
        Assertions.assertEquals(3, phase);

        latch.await();
        one.join();

        Assertions.assertEquals(0, phaser.getRegisteredParties());
        Assertions.assertTrue(phaser.getPhase() < 0);
        Assertions.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testOnAdvance() throws Exception {
        phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                // Возвращаем true, чтобы завершить фазы после первой фазы
                return phase > 0;
            }
        };

        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertEquals(0, phaser.getPhase());

        Thread thread = new Thread(() -> {
            simulateThreadWork(200);
            int phase = phaser.arriveAndAwaitAdvance();
            Assertions.assertEquals(1, phase);
            Assertions.assertEquals(1, phaser.getPhase());
            
            // Вторая фаза не должна начаться из-за onAdvance
            phase = phaser.arrive();
            Assertions.assertTrue(phase < 0);
            Assertions.assertTrue(phaser.isTerminated());
            
            latch.countDown();
        });
        thread.start();

        simulateThreadWork(500);
        int phase = phaser.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, phase);
        Assertions.assertEquals(1, phaser.getPhase());

        // Вторая фаза не должна начаться из-за onAdvance
        phase = phaser.arrive();
        Assertions.assertTrue(phase < 0);
        Assertions.assertTrue(phaser.isTerminated());

        latch.await();
        thread.join();

        Assertions.assertEquals(0, phaser.getRegisteredParties());
        Assertions.assertTrue(phaser.getPhase() < 0);
        Assertions.assertTrue(phaser.isTerminated());
    }

    private void simulateThreadWork(long mills, String... label) {
        String s = label.length == 0 ? "" : label[0] + ": ";
        System.out.println(s + Thread.currentThread().getName() + " is starting its work...");
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s + Thread.currentThread().getName() + " has finished its work!");
    }
}