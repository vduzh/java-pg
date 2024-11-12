package by.duzh.jse.util.concurrent.locks;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    private StampedLock lock;

    @Test
    public void testReadLock() throws Exception {
        //lock.readLock()

        throw new RuntimeException("ReadWriteLockTest");
    }

    @Test
    public void testWriteLock() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        var map = new HashMap<String, String>();



        //lock.writeLock()

        throw new RuntimeException("ReadWriteLockTest");
    }
}
