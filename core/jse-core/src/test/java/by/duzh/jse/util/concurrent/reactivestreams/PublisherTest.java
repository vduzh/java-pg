package by.duzh.jse.util.concurrent.reactivestreams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class PublisherTest {
    private Flow.Publisher<String> publisher;

    @Before
    public void init() throws Exception {
        publisher = new SubmissionPublisher<>();
    }

    @After
    public void destroy() {
        ((SubmissionPublisher<String>) publisher).close();
    }

    @Test
    public void testSubscribe() {
        publisher.subscribe(new TestSubscriber<String>());
    }
}
