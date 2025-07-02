package by.duzh.pg.app.spring.cloud.foo.config;

import by.vduzh.pg.customer.event.FooEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

/**
 * Declare channels
 */
@Configuration
public class StreamConfig {

    /**
     * Defines a functional bean named <code>sendFooEventToRabbitMQ</code> in the Spring context.
     * <p>
     * This Supplier provides a reactive stream of {@link Message} objects carrying {@link FooEvent} payloads.
     * <p>
     * Currently returns an empty stream, as message publishing is delegated to
     * {@link org.springframework.cloud.stream.function.StreamBridge}.
     *
     * @return a Supplier that produces a {@link Flux} of {@link Message}&lt;{@link FooEvent}&gt;
     */
    @Bean
    public Supplier<Flux<Message<FooEvent>>> sendFooEventToRabbitMQ() {
        return Flux::empty;
    }

    @Bean
    public Supplier<Flux<Message<FooEvent>>> sendFooEventToKafka() {
        return Flux::empty;
    }
}
