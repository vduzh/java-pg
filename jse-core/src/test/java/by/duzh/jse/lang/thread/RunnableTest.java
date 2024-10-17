package by.duzh.jse.lang.thread;

import org.junit.Test;

public class RunnableTest {
    @Test
    public void createThread() {
        Runnable target = new Runnable() {
            @Override
            public void run() {
                // run is an entry point!
            }
        };

        new Thread(target).start();
    }
}
