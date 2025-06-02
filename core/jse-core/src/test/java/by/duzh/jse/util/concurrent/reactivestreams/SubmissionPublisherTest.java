package by.duzh.jse.util.concurrent.reactivestreams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.stream.LongStream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SubmissionPublisherTest {
    private SubmissionPublisher<Long> publisher;

    @Test
    public void testDefaultCreate() throws Exception {
        publisher = new SubmissionPublisher<>();
        publisher.close();
    }

    @Test
    public void testGetEachSubscriberMaxBufferCapacity() throws Exception {
        try (var publisher = new SubmissionPublisher<>()) {
            Assertions.assertEquals(256, publisher.getMaxBufferCapacity());
        };
    }

    @Test
    public void testSubmitToDeliverAllSubscribers() throws Exception {
        try (var publisher = new SubmissionPublisher<>()) {
            publisher.submit(1);
        };
    }

    @Test
    public void testOfferWithTimeout() throws Exception {
        try (var publisher = new SubmissionPublisher<>()) {
            // Add Subscriber

            // fill in the buffer
            LongStream.range(1, 257).forEach(publisher::submit);

            // make one more attempt to publish
            var res = publisher.offer(257, 3, SECONDS, (a, b) -> true);
            System.out.println(res);

/*
            res = publisher.offer(257, 3, SECONDS, (a, b) -> false);
            System.out.println(res);
*/
            //Assertions.assertEquals(256, publisher.getMaxBufferCapacity());
        };

/*
        try (var publisher = new SubmissionPublisher<>()) {
            publisher.getMaxBufferCapacity()
            publisher.offer(1, 1, SECONDS, (a, b) -> false);
            Assertions.assertEquals(256, publisher.getMaxBufferCapacity());
        };
*/
    }

    @Test
    public void testConsume() throws Exception {
        CompletableFuture<Void> res = null;

        try (var publisher = new SubmissionPublisher<>()) {
            res = publisher.consume(System.out::println);
            publisher.submit(1L);
        };

        if (res != null) {
            // wait wor the end of subscriber
            res.get();
        }
    }

    @Test
    public void testClose() throws Exception {
        var publisher = new SubmissionPublisher<>();

        // issues onComplete signals to current subscribers
        publisher.close();
    }

}
