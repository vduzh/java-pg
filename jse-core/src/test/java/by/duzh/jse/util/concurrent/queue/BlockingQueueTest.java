package by.duzh.jse.util.concurrent.queue;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class BlockingQueueTest {
    private BlockingQueue<String> blockingQueue;

    @Test
    public void createUnboundedQueue() {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();

        Assert.assertEquals(Integer.MAX_VALUE, blockingQueue.remainingCapacity());
    }

    @Test
    public void createBoundedQueue() {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(10);

        Assert.assertEquals(Integer.MAX_VALUE, blockingQueue.remainingCapacity());
    }

    @Test(expected = IllegalStateException.class)
    public void testAdd() throws Exception {
        blockingQueue = new LinkedBlockingDeque<>(1);

        Assert.assertTrue(blockingQueue.add("A"));

        // throws IllegalStateException as the queue is full
        blockingQueue.add("B");
    }

    @Test
    public void testPutWhichIsBlocked() throws Exception {
        blockingQueue = new LinkedBlockingDeque<>(1);
        blockingQueue.put("A");

        // Run a task that will consume the value in 2 sec
        new Thread(() -> {
            try {
                SECONDS.sleep(2);
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // waiting for a queue
        blockingQueue.put("B");
    }

    @Test
    public void testOffer() throws Exception {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(1);

        Assert.assertTrue(blockingQueue.offer("A"));
        Assert.assertFalse(blockingQueue.offer("B"));
        Assert.assertFalse(blockingQueue.offer("C", 1, SECONDS));

        // Run a task that will consume the value in 2 sec
        new Thread(() -> {
            try {
                SECONDS.sleep(2);
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Assert.assertTrue(blockingQueue.offer("D", 4, SECONDS));
    }

    @Test
    public void testTakeWhichIsBlocked() throws Exception {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(1);

        blockingQueue.put("A");
        Assert.assertEquals("A", blockingQueue.take());

        // Run a task that will consume the value in 2 sec
        new Thread(() -> {
            try {
                SECONDS.sleep(2);
                blockingQueue.put("B");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // wait for the producer
        Assert.assertEquals("B", blockingQueue.take());
    }

    @Test
    public void testPoll() throws Exception {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(1);

        Assert.assertNull(blockingQueue.poll(1, SECONDS));

        // Run a task that will consume the value in 2 sec
        new Thread(() -> {
            try {
                SECONDS.sleep(1);
                blockingQueue.put("B");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // wait for the producer
        Assert.assertEquals("B", blockingQueue.poll(2, SECONDS));
    }

    @Test
    public void testProducerConsumer() throws Exception {
        final int SIZE = 10;
        final int POISON_PILL = Integer.MAX_VALUE;

        CountDownLatch latch = new CountDownLatch(1);

        var queue = new LinkedBlockingDeque<Integer>(4);

        // producer
        new Thread(() -> {
            try {
                for (int i = 0; i < SIZE; i++) {
                    queue.put(ThreadLocalRandom.current().nextInt(100));
                }

                // notify that no more data will come
                queue.put(POISON_PILL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // consumer
        new Thread(() -> {
            try {
                while (true) {
                    int value = queue.take();
                    if (value == POISON_PILL) {
                        break;
                    }
                    MILLISECONDS.sleep(200);
                    System.out.printf("Received: %d!%n", value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            latch.countDown();
        }).start();

        latch.await();
    }

    @Test
    public void testProducerConsumerViaExecutor() throws Exception {
        final int SIZE = 10;
        final int POISON_PILL = Integer.MAX_VALUE;

        var queue = new LinkedBlockingDeque<Integer>(4);

        ExecutorService service = Executors.newFixedThreadPool(2);

        // producer
        service.submit((Callable<?>) () -> {
            for (int i = 0; i < SIZE; i++) {
                queue.put(ThreadLocalRandom.current().nextInt(100));
            }
            // notify that no more data will come
            queue.put(POISON_PILL);
            return true;
        });

        // consumer
        Future<Boolean> future = service.submit(() -> {
            while (true) {
                int value = queue.take();
                if (value == POISON_PILL) {
                    break;
                }
                MILLISECONDS.sleep(200);
                System.out.printf("Received: %d!%n", value);
            }
            return true;
        });

        future.get();

        service.shutdown();
    }

    @Test
    public void testLogWriter() throws Exception {
        class LogWriter {
            final private BlockingQueue<String> queue;
            final private Thread thread;

            final private AtomicBoolean isClosed = new AtomicBoolean(false);

            public LogWriter(int capacity) {
                queue = new LinkedBlockingDeque<>(capacity);

                thread = new Thread(() -> {
                    try {
                        while (true) {
                            // get a message from the quue
                            String message = queue.take();
                            // write message to a log file
                            System.out.printf("Logger thread: [%s]%n", message);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Logger thread: has been interrupted!");
                        Thread.currentThread().interrupt();
                    }
                });
                thread.start();
            }

            public void log(String s) {
                if (isClosed.get()) {
                    throw new IllegalStateException("Logger is closed!");
                }

                try {
                    queue.put(s);
                } catch (InterruptedException e) {
                    System.out.printf("Logger: can't log %s as the logger is closed!%n", s);
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(e);
                }
            }

            public void close() {
                isClosed.set(true);
                thread.interrupt();
            }
        }

        LogWriter logWriter = new LogWriter(10);
        try {
            var producers = IntStream.range(1, 2).mapToObj(i -> "Producer#" + i)
                    .map(s -> (Runnable) () -> {
                        while (true) {
                            if (Thread.currentThread().isInterrupted()) {
                                //System.out.println(Thread.currentThread().getName() + ": isInterrupted = true");
                                Thread.currentThread().interrupt();
                                break;
                            }

                            try {
                                logWriter.log(s + ": " + ThreadLocalRandom.current().nextInt(100));
                            } catch (IllegalStateException e) {
                                break;
                            }

                            try {
                                MILLISECONDS.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    })
                    .map(Thread::new)
                    .collect(Collectors.toList());

            producers.forEach(Thread::start);

            // Allow working for a couple of seconds
            SECONDS.sleep(3);

            // Interrupt the producers
            producers.forEach(Thread::interrupt);

        } finally {
            logWriter.close();
        }

        System.out.println("END");
    }
}
