package by.duzh.jse.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

record Point(int x, int y) {
}

public class RecordTest {
    @Test
    public void testJDK14Create() throws Exception {
        Point point = new Point(1, 2);
        Assertions.assertEquals(1, point.x());
        Assertions.assertEquals(2, point.y());
    }
}
