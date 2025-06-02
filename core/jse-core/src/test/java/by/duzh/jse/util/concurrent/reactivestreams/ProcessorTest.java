package by.duzh.jse.util.concurrent.reactivestreams;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ProcessorTest {

    static class SimpleProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
        final private HashMap<Integer, String> cache = new HashMap<>();

        protected Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.printf("%s.onSubscribe is working...%n", getClass().getSimpleName());
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            System.out.printf("%s.onNext with [%s] is working....%n", getClass().getSimpleName(), item);

            if (!cache.containsKey(item)) {
                cache.put(item, "Value - " + item);
            }
            submit(cache.get(item));

            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.printf("%s.onError gets called%n", getClass().getSimpleName());
        }

        @Override
        public void onComplete() {
            System.out.printf("%s.onComplete has been called%n", getClass().getSimpleName());
        }
    }

    @Test
    public void test() throws Exception {
        var publisher = new SubmissionPublisher<Integer>();

        var processor = new SimpleProcessor();
        publisher.subscribe(processor);

        Flow.Subscriber<String> subscriber = new TestSubscriber<String>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                super.onSubscribe(subscription);
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                super.onNext(item);
                subscription.request(1);
            }
        };
        processor.subscribe(subscriber);

        publisher.submit(123);
    }
}
