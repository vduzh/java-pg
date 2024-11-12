package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;
import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsTest {
    private IntSummaryStatistics statistics;

    @Before
    public void init() {
        statistics = new IntSummaryStatistics();
    }

    @Test
    public void test() {
        statistics.accept(1);
        statistics.accept(2);
        statistics.accept(3);
        statistics.accept(4);
        statistics.accept(5);

        Assert.assertEquals(15, statistics.getSum());
        Assert.assertEquals(5, statistics.getCount());
        Assert.assertEquals(3, statistics.getAverage(), 0);
        Assert.assertEquals(5, statistics.getMax());
        Assert.assertEquals(1, statistics.getMin());
    }
}
