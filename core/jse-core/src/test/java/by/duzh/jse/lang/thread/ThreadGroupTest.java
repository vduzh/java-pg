package by.duzh.jse.lang.thread;

import org.junit.Assert;
import org.junit.Test;

public class ThreadGroupTest {
    private ThreadGroup threadGroup;

    @Test
    public void createWithName() {
        threadGroup = new ThreadGroup("Test");
        Assert.assertEquals("Test", threadGroup.getName());
    }

    @Test
    public void createWithParentThreadGroup() {
        var parentThreadGroup = new ThreadGroup("Parent");
        threadGroup = new ThreadGroup(parentThreadGroup, "Test");
    }

    @Test
    public void getParent() {
        threadGroup = new ThreadGroup(new ThreadGroup("Parent"), "Test");
        Assert.assertEquals("Parent", threadGroup.getParent().getName());
    }

    @Test
    public void getDefaultAsParent() {
        Assert.assertEquals("main", new ThreadGroup("Test").getParent().getName());
    }

    @Test
    public void testInterrupt() {
        // Interrupt all the threads in the group

        threadGroup = new ThreadGroup("Test");

    }
}