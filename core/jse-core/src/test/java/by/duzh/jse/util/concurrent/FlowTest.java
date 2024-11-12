package by.duzh.jse.util.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;
import java.util.stream.LongStream;

abstract class AbstractSubscriber<T> implements Flow.Subscriber<T> {
    protected String name;
    protected Flow.Subscription subscription;

    public AbstractSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.printf("onSubscribe: %s subscribed.%n", name);
        this.subscription = subscription;
    }

    @Override
    public void onNext(T item) {
        System.out.printf("onNext: %s got %s.%n", name, item.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.printf("onError: Error in %s: %s.%n", name, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.printf("onComplete: %s completed its work.%n", name);
    }
};

class RequestItemsAndUnsubscribe extends AbstractSubscriber<Long> {
    protected int counter;
    protected int maxCount;
    protected final int portionSize;
    protected long delay;

    public RequestItemsAndUnsubscribe(String name, int maxCount, int portionSize, long delay) {
        super(name);
        this.maxCount = maxCount;
        this.portionSize = portionSize;
        this.delay = delay;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        super.onSubscribe(subscription);
        // request the specified number of elements
        subscription.request(portionSize);
    }

    @Override
    public void onNext(Long item) {
        super.onNext(item);
        ++counter;

        if (delay > 0) {
            try {
                Thread.sleep(delay);
                System.out.printf("onNext: %s processed %s.%n", name, item.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // increase the amount of the processed items
        if (counter >= maxCount) {
            System.out.printf("onNext: %s unsubscribed.%n", name);
            subscription.cancel();
            return;
        }

        // request a new portion of data
        subscription.request(portionSize);
    }
};

class FilterProcessor extends AbstractSubscriber<String> implements Flow.Processor<String, Long> {
    private final SubmissionPublisher<Long> publisher;
    private final Predicate<? super String> filter;

    public FilterProcessor(String name, Predicate<? super String> filter) {
        super(name);
        this.filter = filter;
        publisher = new SubmissionPublisher<>();
    }

    public void onSubscribe(Flow.Subscription subscription) {
        super.onSubscribe(subscription);
        subscription.request(Long.MAX_VALUE); // request all the elements
    }

    @Override
    public void onNext(String item) {
        super.onNext(item);

        if (filter.test(item)) {
            System.out.println("forwarding: " + item);
            publisher.submit(Long.parseLong(item));
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        publisher.close();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Long> subscriber) {
        publisher.subscribe(subscriber);
    }
}

public class FlowTest {
    @Test
    public void testDefaultBufferSize() {
        int size = Flow.defaultBufferSize();
        Assert.assertEquals(256, size);
    }

    @Test
    public void testCreatePublisher() throws Exception {
        Flow.Publisher<Long> publisher = new SubmissionPublisher<Long>();
        // tear down
        ((SubmissionPublisher<Long>) publisher).close();
    }

    @Test
    public void testCreateSubscriber() throws Exception {
        Flow.Subscriber<Long> subscriber = new RequestItemsAndUnsubscribe("S1", 10, 10, 0);
    }

    @Test
    public void testPublisherSubscribe() throws Exception {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            // call onSubscribe on the subscriber
            publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 10, 10, 0));
        }
    }

    @Test
    public void testSubscriberOnNext() throws Exception {
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>();
        publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 1, 1, 0));

        // call onNext on the subscriber
        publisher.submit(123L);

        // teardown
        publisher.close();
    }

    @Test
    public void testSubscriberOnComplete() throws Exception {
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>();
        publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 1, 1, 0));

        // call onComplete on the subscriber
        publisher.close();
    }

    @Test
    public void testSubscriberOnError() throws Exception {
        // impl of Flow.Publisher<Long>
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 1, 1, 0));

        // call onError on the subscriber
        publisher.closeExceptionally(new Exception("foo"));
    }

    @Test
    public void testSubscriptionRequest() throws Exception {
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>();
        publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 15, 3, 0));

        LongStream.range(1, 16).forEach(publisher::submit);

        Thread.sleep(1500);

        // teardown
        publisher.close();
    }

    @Test
    public void testSubscriptionCancel() throws Exception {
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>()) {
            publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 1, 1, 0));

            // call onSubscribe on the subscriber maxCount == 1
            publisher.submit(1L);

            Thread.sleep(1500);
            Assert.assertFalse(publisher.hasSubscribers());
        }
    }

    @Test
    public void testBackpressure() throws Exception {
        Executor executor = ForkJoinPool.commonPool();
        int maxBufferCapacity = 10;

        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<Long>(executor, maxBufferCapacity)) {
            publisher.subscribe(new RequestItemsAndUnsubscribe("S1", 1000, 5, 1000));

            LongStream.range(1, 101).forEach(value -> {
                publisher.offer(value, (subscriber, element) -> {
                    System.out.printf("Dropped element %d.%n", element);
                    return false;
                });
            });

            Thread.sleep(20000);
        }
    }

    @Test
    public void testProcessor() throws Exception {
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<String>()) {
            FilterProcessor processor = new FilterProcessor("FilterProcessor", s -> Integer.parseInt(s) < 6);
            publisher.subscribe(processor);
            processor.subscribe(new RequestItemsAndUnsubscribe("S1", 100, 100, 0));

            LongStream.range(1, 101).mapToObj(String::valueOf).forEach(publisher::submit);

            Thread.sleep(20000);
        }
    }
}