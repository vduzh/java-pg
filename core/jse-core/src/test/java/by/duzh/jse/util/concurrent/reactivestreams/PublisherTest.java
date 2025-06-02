package by.duzh.jse.util.concurrent.reactivestreams;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class PublisherTest {
    private Flow.Publisher<String> publisher;

    @BeforeEach
    public void init() throws Exception {
        publisher = new SubmissionPublisher<>();
    }

    @AfterEach
    public void destroy() {
        ((SubmissionPublisher<String>) publisher).close();
    }

    @Test
    public void testSubscribe() {
        publisher.subscribe(new TestSubscriber<String>());
    }
}
