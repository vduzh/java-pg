package by.duzh.jse.lang.thread;

import org.junit.Test;


class Locker {
    public static final Object locker1 = new Object();
    public static final Object locker2 = new Object();
}

public class ThreadLockTest {
    // 2 threads are waiting and doing nothing
    @Test
    public void testDeadLock() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                synchronized (Locker.locker1) {
                    // simulate work
                    Thread.sleep(1000);
                    synchronized (Locker.locker2) {
                        // simulate work
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                //synchronized (Locker.locker2) { // dead lock
                synchronized (Locker.locker1) {
                    // simulate work
                    Thread.sleep(500);
                    //synchronized (Locker.locker1) { // dead lock
                    synchronized (Locker.locker2) {
                        // simulate work
                        Thread.sleep(500);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        //System.out.println("Finish");
    }

    // 2 threads does some job without any progress:
    // - one does some staff, other compensates that job
    @Test
    public void testLiveLock() {
        //TODO: create test
    }

    // Les prioritized thread are waiting and and can not run.
    @Test
    public void testLockStarvation() {
        //TODO: create test
    }
}