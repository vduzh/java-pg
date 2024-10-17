package by.duzh.jse.util.time;

import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskTest {
    public static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("MyTimerTask is working...");
        }
    }

    private Timer timer;

    private MyTimerTask timerTask;

    @Before
    public void init() {
        timer = new Timer();
        timerTask = new MyTimerTask();
    }

    @Test
    public void testSchedule() {
        timer.schedule(timerTask, 1000);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        timer.cancel();

    }
}
