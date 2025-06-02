package by.duzh.jse.util.concurrent.locks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Disabled
public class ReadWriteLockTest {
    private ReadWriteLock lock;

    ExecutorService executor;

    @BeforeEach
    public void init() throws Exception {
        lock = new ReentrantReadWriteLock();
        executor = Executors.newCachedThreadPool();
    }

    @AfterEach
    public void destroy() throws Exception {
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testWriteLock() throws Exception {
        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    @Test
    public void testReadLock() throws Exception {
        executor.submit(() -> {
            lock.readLock().lock();
            try {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.readLock().unlock();
            }
        });
    }

    @Test
    public void testReadAndWriteLock() throws Exception {
        var map = new HashMap<String, String>();

        int size = 3;

        for (int i = 0; i < size; i++) {
            final int j = i + 1;
            executor.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep((j - 1) * 500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.printf("Reader%d: trying to get the lock... %n", j);
                lock.readLock().lock();
                System.out.printf("Reader%d: GOT the lock %n", j);
                try {
                    map.get("foo");
                    try {
                        TimeUnit.SECONDS.sleep(j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Reader%d: GOT data from the shared res %n", j);
                } finally {
                    System.out.printf("Reader%d: is about to release the lock... %n", j);
                    lock.readLock().unlock();
                    System.out.printf("Reader%d: released the lock!%n", j);
                }
            });
        }

        executor.submit(() -> {
            System.out.println("Writer is trying to get the lock...");
            lock.writeLock().lock();
            System.out.println("Writer GOT the lock!");
            try {
                map.put("foo", "bar");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Writer put data to a shared res");
            } finally {
                System.out.println("Writer is about to releas the lock!");
                lock.writeLock().unlock();
                System.out.println("Writer released the lock!");
            }
        });
    }

    @Test
    public void testFixedSizeLog() throws Exception {

        // Writer
        Future<?> f = executor.submit(() -> {
            for (int i = 0; i < 4; i++) {
                TimeUnit.MILLISECONDS.sleep(500);

                System.out.println("Writer is trying to get the lock...");
                lock.writeLock().lock();
                System.out.println("Writer GOT the lock!");

                try {
                    File file = new File("d:\\1\\1.log");
                    if (file.length() >= 3000) {
                        int n = 0;
                        while (new File("d:\\1\\1.log-" + n).exists()) {
                            n++;
                        }
                        file.renameTo(new File("d:\\1\\1.log-" + n));

                        file = new File("d:\\1\\1.log");
                    }

                    try {
                        FileOutputStream fos = new FileOutputStream(file, true);
                        //fos.write(("Text " + i + "\n").getBytes());
                        fos.write(new byte[1000]);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Writer put data(%d) to a shared res%n", i);
                } finally {
                    System.out.println("Writer is about to releas the lock!");
                    lock.writeLock().unlock();
                    System.out.println("Writer released the lock!");
                }
            }

            return true;
        });

        // Reader
        for (int i = 0; i < 3; i++) {

            final int j = i + 1;
            executor.submit(() -> {
                for (int k = 0; k < 5; k++) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(j * 200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lock.readLock().lock();
                    try {
                        File file = new File("d:\\1\\1.log");
                        //BufferedReader reader = new BufferedReader(new FileReader(file));
                    /*String s = "";
                    while ((s = reader.readLine()) != null) {
                        System.out.printf("Reader%d: GOT data from the shared res [%s] %n", j, s);
                    }
                    reader.close();*/

                        FileInputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[3000];

                        int size = is.read(bytes);
                        System.out.printf("Reader%d: GOT %d data from file %n", j, size);
                        is.close();

                    } finally {
                        System.out.printf("Reader%d: is about to release the lock... %n", j);
                        lock.readLock().unlock();
                        System.out.printf("Reader%d: released the lock!%n", j);
                    }
                }
                return true;
            });
        }

        //f.get();

        TimeUnit.SECONDS.sleep(10);

    }
}