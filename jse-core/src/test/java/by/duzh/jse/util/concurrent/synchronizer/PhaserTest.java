package by.duzh.jse.util.concurrent.synchronizer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

//TODO: write the rest tests
public class PhaserTest {
    private Phaser phaser;

    @Test
    public void testCreate() throws Exception {
        // With no parties registered
        phaser = new Phaser();
        Assert.assertEquals(0, phaser.getRegisteredParties());

        // With one party (the current thread) registered
        phaser = new Phaser(2);
        Assert.assertEquals(2, phaser.getRegisteredParties());
    }

    @Test
    public void testGetPhase() throws Exception {
        phaser = new Phaser();
        Assert.assertEquals(0, phaser.getPhase());
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

        Assert.assertEquals(5, phaser.getRegisteredParties());
    }

    @Test
    public void testWait() throws Exception {
        phaser = new Phaser(1);
        Assert.assertEquals(0, phaser.getPhase());

        Thread thread = new Thread(() -> {
            simulateThreadWork(200);

            int phase = phaser.arrive();
            System.out.println(phase);
            Assert.assertEquals(1, phase);
            Assert.assertEquals(1, phaser.getPhase());

            phaser.arriveAndDeregister();
        });
        thread.start();

        simulateThreadWork(500);
        int phase = phaser.arrive();
        System.out.println(phase);
        Assert.assertEquals(1, phase);
        Assert.assertEquals(1, phaser.getPhase());

        phaser.arriveAndDeregister();

        thread.join();

        Assert.assertEquals(0, phaser.getRegisteredParties());
        Assert.assertTrue(phaser.getPhase() < 0);
        Assert.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testArriveAndWaitAdvance() throws Exception {
        phaser = new Phaser(2);
        Assert.assertEquals(0, phaser.getPhase());

        Thread thread = new Thread(() -> {
            simulateThreadWork(2000);

            int phase = phaser.arriveAndAwaitAdvance();
            Assert.assertEquals(1, phase);
            Assert.assertEquals(1, phaser.getPhase());

            phaser.arriveAndDeregister();
        });
        thread.start();

        simulateThreadWork(300);

        int phase = phaser.arriveAndAwaitAdvance();
        Assert.assertEquals(1, phase);
        Assert.assertEquals(1, phaser.getPhase());

        phaser.arriveAndDeregister();

        thread.join();

        Assert.assertEquals(0, phaser.getRegisteredParties());
        Assert.assertTrue(phaser.getPhase() < 0);
        Assert.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testSeveralPhasesWithArriveAndAwaitAdvance() throws Exception {
        phaser = new Phaser(2);

        Thread one = new Thread(() -> {
            int phase;

            simulateThreadWork(100, "Phase1");
            phase = phaser.arriveAndAwaitAdvance();
            Assert.assertEquals(1, phase);

            simulateThreadWork(1500, "Phase2");
            phase = phaser.arriveAndAwaitAdvance();
            Assert.assertEquals(2, phase);

            phase = phaser.arriveAndDeregister();
            Assert.assertEquals(2, phase);
        });
        one.start();

        int phase;

        simulateThreadWork(500, "Phase1");
        phase = phaser.arriveAndAwaitAdvance();
        Assert.assertEquals(1, phase);

        simulateThreadWork(1000, "Phase2");
        phase = phaser.arriveAndAwaitAdvance();
        Assert.assertEquals(2, phase);

        phase = phaser.arriveAndDeregister();
        Assert.assertEquals(2, phase);

        Assert.assertEquals(0, phaser.getRegisteredParties());
        Assert.assertTrue(phaser.getPhase() < 0);
        Assert.assertTrue(phaser.isTerminated());
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

        Thread one = new Thread(() -> {
            int phase;

            simulateThreadWork(100, "Phase1");
            phase = phaser.arrive();
            Assert.assertEquals(1, phase);

            simulateThreadWork(1500, "Phase2");
            phase = phaser.arrive();
            Assert.assertEquals(2, phase);

            phase = phaser.arriveAndDeregister();
            Assert.assertEquals(2, phase);
        });
        one.start();

        int phase;

        simulateThreadWork(500, "Phase1");
        phase = phaser.arrive();
        Assert.assertEquals(1, phase);

        simulateThreadWork(1000, "Phase2");
        phase = phaser.arrive();
        Assert.assertEquals(2, phase);

        phase = phaser.arriveAndDeregister();
        Assert.assertEquals(2, phase);

        Assert.assertEquals(0, phaser.getRegisteredParties());
        Assert.assertTrue(phaser.getPhase() < 0);
        Assert.assertTrue(phaser.isTerminated());
    }

    @Test
    public void testOnAdvance() {
        throw new RuntimeException("testOnAdvance needs to be tested!!!");
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