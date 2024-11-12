package by.duzh.jse.lang.thread;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.State;

public class ThreadTest {
    private Thread thread;

    @Test
    public void createViaExtension() {
        // Extending the thread class
        thread = new Thread() {
            @Override
            public void run() {
                ;
            }
        };
    }

    @Test
    public void createWithRunnable() {
        // Via the Runnable interface
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ;
            }
        });

        // Use lambda expression with Runnable
        thread = new Thread(() -> {
        });
    }

    @Test
    public void createThreadInGroup() {
        var group = new ThreadGroup("MyGroup");

        // Use lambda expression with Runnable
        thread = new Thread(group, () -> {
            System.out.println("Create thread in group");
        });
    }

    @Test
    public void testStart() {
        thread = new Thread(() -> {
        });
        thread.start();

        // Waits for the thread to die
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Restore interruption status
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testGetId() {
        Assert.assertTrue(Thread.currentThread().getId() > 0);
    }

    @Test
    public void testGetName() {
        thread = new Thread(() -> {
        }, "Test");
        Assert.assertEquals("Test", thread.getName());
    }

    @Test
    public void testSetName() {
        thread = new Thread(() -> {
        });
        thread.setName("Test");
        Assert.assertEquals("Test", thread.getName());
    }

    @Test
    public void testGetPriority() {
        thread = new Thread(() -> {
        });
        Assert.assertEquals(Thread.NORM_PRIORITY, thread.getPriority());
    }

    @Test
    public void testSetPriority() {
        thread = new Thread(() -> {
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        Assert.assertEquals(Thread.NORM_PRIORITY, thread.getPriority());
    }

    @Test
    public void testIsAlive() throws InterruptedException {
        thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Restore interruption status
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

        Assert.assertTrue(thread.isAlive());

        thread.join();
        Assert.assertFalse(thread.isAlive());
    }

    @Test
    public void testJoin() throws InterruptedException {
        boolean[] finished = {false, false};

        thread = new Thread(() -> finished[0] = true);
        thread.start();
        Thread thread2 = new Thread(() -> finished[1] = true);
        thread2.start();

        // Tells the main thread to wait until the threads finish.
        thread.join();
        thread2.join();

        // When the threads finish, the main one will resume
        Assert.assertTrue(finished[0] && finished[1]);
    }

    @Test
    public void testCurrentThread() throws InterruptedException {
        thread = Thread.currentThread();
        Assert.assertEquals("main", thread.getName());

        final var result = new StringBuilder();
        thread = new Thread(() -> {
            result.append(Thread.currentThread().getName());
        }, "test");
        thread.start();

        thread.join();
        Assert.assertEquals("test", result.toString());
    }

    @Test
    public void testSleep() throws InterruptedException {
        boolean[] finished = {false};

        thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                finished[0] = true;
            } catch (InterruptedException e) {
                // Restore interruption status
                Thread.currentThread().interrupt();
            }

        });
        thread.start();

        Assert.assertFalse(finished[0]); // thread is sleeping now...

        thread.join();
        Assert.assertTrue(finished[0]); // thread has already finished
    }

    @Test
    public void testGetState() throws InterruptedException {
        // New -> New
        // Start -> Read (Ready or Running)
        // Finish - Terminated

        thread = new Thread(() -> {
            Assert.assertEquals(State.RUNNABLE, thread.getState());
        });

        Assert.assertEquals(State.NEW, thread.getState());
        thread.start(); // Goes to the Runnable state

        thread.join();
        Assert.assertEquals(State.TERMINATED, thread.getState());
    }

    @Test
    public void testWaitNotify() throws Exception {
        class SyncObj {
            private Integer value;

            public synchronized void sendMessage(int msg) throws InterruptedException {
                System.out.println("Producer: sending value " + msg);

                while (value != null) {
                    System.out.println("Producer: Waiting for " + msg);
                    wait();
                }

                value = msg;
                System.out.println("Producer: notify for " + msg);
                notify();
            }

            public synchronized int consume() throws InterruptedException {
                System.out.println("Consumer: trying to consume");
                while (value == null) {
                    System.out.println("Consumer: Waiting...");
                    wait();
                }

                int res = value;
                System.out.println("Consumer: consumed " + value);
                value = null;
                System.out.println("Consumer: notifying ...");
                notify();
                return res;
            }
        }

        SyncObj syncObj = new SyncObj();

        Thread producer = new Thread(new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                while (count < 10) {
                    ++count;
                    try {
                        syncObj.sendMessage(count);
                    } catch (InterruptedException e) {
                        // restore interruption status
                        Thread.currentThread().interrupt();
                        // Leave the thread
                        return;
                    }
                    System.out.printf("Producer: value %s has been sent\n", count);
                }
            }
        });

        int res = 0;
        Thread consumer = new Thread(new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                while (count < 10) {
                    int value = 0;
                    try {
                        value = syncObj.consume();
                    } catch (InterruptedException e) {
                        // restore interruption status
                        Thread.currentThread().interrupt();
                        // Leave the thread
                        return;
                    }
                    ++count;
                    System.out.println("Consumer: received " + value);
                }
            }
        });

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();

        System.out.println("Finished!");
    }

    @Test
    public void testWaitNotifyAllViaSyncBlock() throws Exception {
        class MessageHolder {
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String pop() {
                var result = text;
                text = null;
                return result;
            }
        }

        class Sender implements Runnable {
            private final MessageHolder messageHolder;

            private int counter;

            public Sender(MessageHolder holder, int counter) {
                this.messageHolder = holder;
                this.counter = counter;
            }

            @Override
            public void run() {
                while (counter > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (messageHolder) {
                        System.out.printf("%s: trying to send for %d \n", Thread.currentThread().getName(), counter);
                        while (messageHolder.getText() != null) {
                            System.out.printf("%s: Data is not taken! Have to wait for!", Thread.currentThread().getName());
                            try {
                                messageHolder.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.printf("%s: is able to send %d \n", Thread.currentThread().getName(), counter);
                        var value = "{" + counter + "}";
                        messageHolder.setText(value);
                        System.out.printf("%s: have sent %s \n", Thread.currentThread().getName(), value);
                        --counter;
                        System.out.printf("%s: is notifying receivers \n", Thread.currentThread().getName());
                        messageHolder.notifyAll();
                    }
                }
            }
        }
        ;

        class Receiver implements Runnable {
            private final MessageHolder messageHolder;

            private int counter;

            public Receiver(MessageHolder holder, int counter) {
                this.messageHolder = holder;
                this.counter = counter;
            }

            @Override
            public void run() {
                while (counter > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (messageHolder) {
                        System.out.printf("%s: trying to consume for %d \n", Thread.currentThread().getName(), counter);
                        while (messageHolder.getText() == null) {
                            try {
                                System.out.printf("%s: No data. Have to wait!\n", Thread.currentThread().getName());
                                messageHolder.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.printf("%s: is able to receive for %d \n", Thread.currentThread().getName(), counter);
                        var value = messageHolder.pop();
                        System.out.printf("%s: Received %s \n", Thread.currentThread().getName(), value);
                        --counter;
                        System.out.printf("%s: is notifying the sender \n", Thread.currentThread().getName());
                        messageHolder.notifyAll();
                    }
                }
            }
        }
        ;

        var messageHolder = new MessageHolder();

        Thread t1 = new Thread(new Sender(messageHolder, 10), "Sender");
        Thread t2 = new Thread(new Receiver(messageHolder, 6), "Receiver #1");
        Thread t3 = new Thread(new Receiver(messageHolder, 4), "Receiver #2");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    @Test
    public void suspendViaWaitNotify() throws Exception {
        class Suspended implements Runnable {

            private boolean suspendFlag;

            @Override
            public void run() {
                try {
                    for (int i = 1; i < 20; i++) {
                        // mock some job
                        Thread.sleep(1000);
                        System.out.printf("%s: have done some job\n", Thread.currentThread().getName());

                        synchronized (this) {
                            while (suspendFlag) {
                                System.out.printf("%s: I have to wait...\n", Thread.currentThread().getName());
                                wait();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            public synchronized void suspend() {
                suspendFlag = true;
                System.out.printf("%s: suspend - spendFlag: %b\n", Thread.currentThread().getName(), suspendFlag);
            }

            public synchronized void resume() {
                suspendFlag = false;
                System.out.printf("%s: resume - suspendFlag: %b\n", Thread.currentThread().getName(), suspendFlag);
                notify();

            }
        }

        // App code
        final var target = new Suspended();

        var t1 = new Thread(target, "AppThread");
        t1.start();

        Thread.sleep(5000);
        System.out.printf("%s: Suspending the thread...\n", Thread.currentThread().getName());
        target.suspend();

        System.out.println("doing nothing");

        Thread.sleep(4000);
        System.out.printf("%s: Resuming the thread...\n", Thread.currentThread().getName());
        target.resume();

        t1.join();
    }


    @Test
    public void testSetDaemon() {
        Thread daemon = new Thread(() -> {
            try {
                int i = 0;
                while (true) {
                    i++;
                    System.out.println(i + " begins");
                    Thread.sleep(500);
                    System.out.println(i + " ends");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        daemon.setDaemon(true);
        daemon.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testInterrupt() throws Exception {
        var thread = new Thread(() -> {
            while (true) {
                // Decide to interrupt now the current thread.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Somebody has asked us to interrupt the thread.");

                    // clean all the resources and shutdown the thread gracefully!
                    System.out.println("Cleaning all the resources and shutting down the thread gracefully!");
                    return;
                }


                try {
                    System.out.println("I'm going to do some job.");
                    Thread.sleep(10000);
                    System.out.println("The job has done!");
                } catch (InterruptedException e) {
                    System.out.println("Sleep has been interrupted. We will mark the current thread to get interrupted.");
                    // NOTE: Interrupted status is cleared after sleep!!
                    Assert.assertFalse(Thread.currentThread().isInterrupted());
                    // Restore interrupted status
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();

        Thread.sleep(3000);

        // Notify the thread to get interrupted
        thread.interrupt();
    }

    @Test
    public void testInterruptedException() {
        thread = new Thread(() -> {
            while (true) {
                // Interrupt while the thread is sleeping...
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // cleanup sources and stream
                }
            }
        });
        thread.start();

        try {
            Thread.sleep(2000);
            // Notify the thread to get interrpated
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInterruptFromScheduledTask() throws Exception {
        ScheduledExecutorService cancelExecutor = Executors.newScheduledThreadPool(1);

        var t = new Thread(() -> {
            while (true) {
                // Decide to interrupt now the current thread.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Somebody has asked us to interrupt the thread.");

                    // clean all the resources and shutdown the thread gracefully!
                    System.out.println("Cleaning all the resources and shutting down the thread gracefully!");
                    return;
                }
            }
        });
        t.start();

        cancelExecutor.schedule(t::interrupt, 3, TimeUnit.SECONDS);

        t.join();
        cancelExecutor.shutdown();
    }

    @Test
    public void testSetUncaughtExceptionHandler() throws Exception {
        thread = new Thread(() -> {
            throw new RuntimeException();
        });
        thread.setName("test");
        thread.setUncaughtExceptionHandler((t, e) -> {
            // process the exception like clean up resources

            Assert.assertEquals("test", t.getName());
            Assert.assertTrue(e instanceof RuntimeException);
        });
        thread.start();

        thread.join();
    }

    @Test
    public void splitterGather() throws Exception {
        class Result {
            private boolean isOpen = true;

            private String value;

            public void close() {
                isOpen = false;
            }

            public boolean isOpen() {
                return isOpen;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        class Gather implements Runnable {

            final private int duration;
            private final Result result;

            public Gather(int duration) {
                this.result = new Result();
                this.duration = duration;
            }

            public Result getResult() {
                return result;
            }

            @Override
            public void run() {
                var s = Thread.currentThread().getName();
                System.out.printf("%s: starting a job of %d seconds \n", s, duration);
                try {
                    TimeUnit.SECONDS.sleep(duration);
                    if (getResult().isOpen)
                        result.setValue("Value " + duration);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.printf("%s: have finished its job \n", s);
            }
        }

        Gather[] gathers = {
                new Gather(2), new Gather(4)
        };

        Thread[] threads = Arrays.stream(gathers)
                .map(Thread::new)
                .toArray(Thread[]::new);

        Arrays.stream(threads)
                .forEach(Thread::start);

        Arrays.stream(threads)
                .forEach(t -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });




/*
        var t2 = new Thread(new Gather(r2, 4), "Gather #2");

        t1.start();
        t2.start();

        t1.join(5000);
        t2.join(5000);

        System.out.println(r1.getValue());
        System.out.println(r2.getValue());
*/
    }

}