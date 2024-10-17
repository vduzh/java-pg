package by.duzh.jse.core;

import org.junit.Assert;
import org.junit.Test;

record Point(int x, int y) {
}

public class RecordTest {
    @Test
    public void testJDK14Create() throws Exception {
        Point point = new Point(1, 2);
        Assert.assertEquals(1, point.x());
        Assert.assertEquals(2, point.y());
    }

    @Test
    public void name() throws Exception {
        throw new Exception("RecordTest!!!!");
    }
}
