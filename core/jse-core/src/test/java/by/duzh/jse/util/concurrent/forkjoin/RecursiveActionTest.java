package by.duzh.jse.util.concurrent.forkjoin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.RecursiveAction;

@Disabled
public class RecursiveActionTest {

    @Test
    public void testDivideAndConquerAction() {

        class CalculateSqrtAction extends RecursiveAction {
            private static final int THRESHOLD = 1_000;

            private final double[] data;
            private final int start, end;

            public CalculateSqrtAction(double[] data, int start, int end) {
                this.data = data;
                this.start = start;
                this.end = end;
            }

            @Override
            protected void compute() {
                if (start - end < THRESHOLD) {
                    for (int i = start; i < end; i++) {
                        data[i] = Math.sqrt(data[i]);
                    }
                } else {
                    // divide into 2 tasks
                    int middle = (start + end) / 2;
                    invokeAll(new CalculateSqrtAction(data, start, middle), new CalculateSqrtAction(data, middle, end));
                }
            }
        }

        // prepare data
        double[] data = new double[100_000];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }

        // Perform an action
        var action = new CalculateSqrtAction(data, 0, data.length);
        action.invoke();

        // Assert
        Assertions.assertEquals(3, data[9], 0.00001);
        Assertions.assertEquals(100, data[10000], 0.00001);
    }
}