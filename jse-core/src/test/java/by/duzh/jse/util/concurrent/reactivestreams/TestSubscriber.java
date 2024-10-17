package by.duzh.jse.util.concurrent.reactivestreams;

import java.util.concurrent.Flow;

public class TestSubscriber<T> implements Flow.Subscriber<T> {
    protected Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.printf("%s.onSubscribe is working...%n", getClass().getSimpleName());
        this.subscription = subscription;
    }

    @Override
    public void onNext(T item) {
        System.out.printf("%s.onNext with [%s] is working....%n", getClass().getSimpleName(), item.toString());
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
