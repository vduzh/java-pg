package by.duzh.jse.util.concurrent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.stream.LongStream;

public class SubmissionPublisherTest {

    @Before
    public void init() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            ;
        }
    }

    @Test
    public void testCreateDefault() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            ;
        }
    }

    @Test
    public void testCreateWithExecutorAndBufferCapacity() {
        Executor executor = Executors.newFixedThreadPool(2);
        int maxBufferCapacity = 512;

        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>(executor, maxBufferCapacity)) {
            ;
        }
    }

    @Test
    public void testCreateWithExecutorAndBufferCapacityAndHandler() {
        Executor executor = Executors.newFixedThreadPool(2);
        int maxBufferCapacity = 512;
        BiConsumer<? super Flow.Subscriber<? super Long>, ? super Throwable> handler = (subscriber, throwable) -> {
            System.out.println("handler is working...");
        };

        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>(executor, maxBufferCapacity, handler)) {
            ;
        }
    }

    @Test
    public void testGetMaxBufferCapacity() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            int maxBufferCapacity = publisher.getMaxBufferCapacity();
            Assert.assertEquals(256, maxBufferCapacity);
        }
    }

    @Test
    public void testSubmit() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            LongStream.range(1, 4).forEach(publisher::submit);
        }
    }

    @Test
    public void testSubmitBlocked() {
        //TODO: implement
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            publisher.submit(1l);
        }
    }

    @Test
    public void testOffer() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            publisher.offer(1l, (subscriber, s) -> {
                // on drop handler
                return false; // cancel resending
            });
        }
    }

    @Test
    public void testOfferWithTimeout() {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            publisher.offer(123l, 1, TimeUnit.SECONDS, (subscriber, s) -> {
                // on drop handler
                return true; // resend
            });
        }
    }

    @Test
    public void testConsume() throws Exception {
        CompletableFuture<Void> res;
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()) {
            // create a consumer
            res = publisher.consume(System.out::println);
            // publish 3 items
            LongStream.range(1, 4).forEach(publisher::submit);
        }

        // wait for the subscriber to finish its job
        res.get();
    }
}
