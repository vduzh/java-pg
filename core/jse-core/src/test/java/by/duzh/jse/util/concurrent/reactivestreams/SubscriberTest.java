package by.duzh.jse.util.concurrent.reactivestreams;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SubscriberTest {
    private SubmissionPublisher<Long> publisher;

    @BeforeEach
    public void init() throws Exception {
        publisher = new SubmissionPublisher<>();
    }

    @AfterEach
    public void destroy() throws Exception {
        publisher.close();
    }

    @Test
    public void testOnSubscribe() throws Exception {
        // prepare data
        var subscriber = new TestSubscriber<Long>();
        publisher.subscribe(subscriber);

        // wait for a while
        SECONDS.sleep(1);
    }

    @Test
    public void testOnNext() throws Exception {
        var subscriber = new TestSubscriber<Long>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                super.onSubscribe(subscription);
                // ask for 1 items -> OnNext will be called by the Publisher
                subscription.request(1);
            }
        };
        publisher.subscribe(subscriber);

        publisher.submit(10L);
        publisher.submit(20L);

        // wait for a while
        SECONDS.sleep(1);
    }

    @Test
    public void testOnComplete() throws Exception {
        var subscriber = new TestSubscriber<Long>();
        publisher.subscribe(subscriber);
    }

    @Test
    public void testOnError() throws Exception {
        var subscriber = new TestSubscriber<Long>();
        publisher.subscribe(subscriber);

        // emulate error!
        //publisher.
    }

    static class NewsServiceSubscriber extends TestSubscriber<String> {
        final Queue<String> mailBox = new ConcurrentLinkedQueue<>();
        final int take;
        final AtomicInteger remaining = new AtomicInteger();

        public NewsServiceSubscriber(int take) {
            this.take = take;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            super.onSubscribe(subscription);

            subscription.request(take);
            remaining.set(take);
        }

        @Override
        public void onNext(String item) {
            super.onNext(item);
            mailBox.offer(item);
        }

        public Optional<String> readDigest() {
            String news = mailBox.poll();
            if (news != null) {
                if (remaining.decrementAndGet() == 0) {
                    subscription.request(take);
                    remaining.set(take);
                }
                return Optional.of(news);
            }
            return Optional.empty();
        }
    }

    @Test
    public void newsService() throws Exception {
        var publisher = new SubmissionPublisher<String>();

        var subscriber = new NewsServiceSubscriber(3);
        publisher.subscribe(subscriber);

        IntStream.range(1, 11).forEach(i -> publisher.submit(String.valueOf(i)));

        int i = 0;
        while (i < 10) {
            var news = subscriber.readDigest();
            if (news.isPresent()) {
                System.out.printf("Getting news: [%s]%n", news.get());
                i++;
            }
        }
    }


}
