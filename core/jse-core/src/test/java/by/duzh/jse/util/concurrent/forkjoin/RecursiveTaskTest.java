package by.duzh.jse.util.concurrent.forkjoin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Disabled
public class RecursiveTaskTest {

    @Test
    public void testDivideAndConquerSumTask() throws Exception {

        class SumTask extends RecursiveTask<Long> {
            private static final int THRESHOLD = 1_000;

            final int[] data;
            final int start, end;

            public SumTask(int[] data, int start, int end) {
                this.data = data;
                this.start = start;
                this.end = end;
            }

            @Override
            protected Long compute() {
                long res = 0;
                if (end - start < THRESHOLD) {
                    // calculate the result
                    for (int i = start; i < end; i++) {
                        res += data[i];
                    }
                    return res;
                } else {
                    // divide into 2 tasks
                    int middle = (start + end) / 2;

                    // create tasks
                    final var task1 = new SumTask(data, start, middle);
                    final var task2 = new SumTask(data, middle, end);

                    // execute tasks asynchronously!
                    //task1.fork();
                    //task2.fork();
                    ForkJoinTask.invokeAll(task1, task2); // this is more optimized that task1.fork() and task2.fork()

                    // get results synchronously!
                    res = task2.join() + task1.join(); // better take t2 and that 1 - optimization
                }
                return res;
            }
        }

        // prepare test data
        final long size = 10_000_000;
        int[] data = new int[(int) size];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }

        // Run asynchronously
        Long res = ForkJoinPool.commonPool().invoke(new SumTask(data, 0, data.length));

        // Verify (n-1)*n/2
        Assertions.assertEquals((size - 1) * size / 2, res.longValue());
    }
}
