package by.duzh.pg.app.spring.cloud.foo.config;

import by.duzh.pg.app.spring.cloud.foo.helper.MessageHelper;
import by.vduzh.pg.foo.dto.FooDto;
import by.vduzh.pg.foo.event.action.FooEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Declare channels
 */
@Configuration
public class StreamConfig {

    @Value("${stream.rabbitmq.intervalSeconds}")
    private long rabbitInterval;


    @Value("${stream.kafka.intervalSeconds}")
    private long kafkaInterval;

    /**
     * Defines a functional bean named <code>sendFooEventToRabbitMQ</code> in the Spring context.
     * <p>
     * This Supplier provides a reactive stream of {@link Message} objects carrying {@link FooEvent} payloads.
     * <p>
     * @return a Supplier that produces a {@link Flux} of {@link Message}&lt;{@link FooEvent}&gt;
     */
    @Bean
    public Supplier<Flux<Message<FooEvent>>> sendFooEventToRabbitMQ() {
        return () -> Flux.interval(Duration.ofSeconds(rabbitInterval))
                .map(sequence -> {
                    FooDto payload = new FooDto(111, "New Foo from Function to RabbitMQ");
                    var event = FooEvent.builder()
                            .id(UUID.randomUUID().toString())
                            .action("foo.created")
                            .payload(payload)
                            .build();
                    return MessageHelper.buildFooMessage(event);
                });
    }

    @Bean
    public Supplier<Flux<Message<FooEvent>>> sendFooEventToKafka() {
        return () -> Flux.interval(Duration.ofSeconds(kafkaInterval))
                .map(sequence -> {
                    FooDto payload = new FooDto(222, "New Foo from Function to Kafka");
                    var event = FooEvent.builder()
                            .id(UUID.randomUUID().toString())
                            .action("foo.created")
                            .payload(payload)
                            .build();
                    return MessageHelper.buildFooMessage(event);
                });
    }
}
