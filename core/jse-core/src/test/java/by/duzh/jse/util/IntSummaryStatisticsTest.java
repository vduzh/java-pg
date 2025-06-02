package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsTest {
    private IntSummaryStatistics statistics;

    @BeforeEach
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

        Assertions.assertEquals(15, statistics.getSum());
        Assertions.assertEquals(5, statistics.getCount());
        Assertions.assertEquals(3, statistics.getAverage(), 0);
        Assertions.assertEquals(5, statistics.getMax());
        Assertions.assertEquals(1, statistics.getMin());
    }
}
