package by.duzh.pg.app.spring.cloud.bar;

import by.vduzh.pg.customer.event.FooEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class StreamConfig {

    @Bean
    public Consumer<Message<FooEvent>> processFooEventFromRabbitMQ() {
        return message -> {
            log.info("Received message from RabbitMQ: {}", message);
            processFooEvent(message);
        };
    }

    @Bean
    public Consumer<Message<FooEvent>> processFooEventFromKafka() {
        return message -> {
            log.info("Received message from Kafka: {}", message);
            processFooEvent(message);
        };
    }

    private void processFooEvent(Message<FooEvent> message) {
        log.info("Processing FooEvent message: {}", message);
        // get event and call an appropriate service
        try {
            FooEvent event = message.getPayload();
            MessageHeaders headers = message.getHeaders();

            log.info("FooEvent: {}", event);
            log.info("Headers: {}", headers);

            // Обработка в зависимости от типа события
            switch (event.getAction()) {
                case "FOO_CREATED":
                    handleFooCreated(event);
                    break;
                case "FOO_UPDATED":
                    handleFooUpdated(event);
                    break;
                default:
                    log.warn("Unknown action: {}", event.getAction());
            }

        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw e; // Для повторной обработки
        }
    }

    private void handleFooCreated(FooEvent event) {
//        log.info("Processing foo creation: fooId={}, email={}",
//                event.getFooId(), event.getEmail());
        log.info("Processing FOO_UPDATED event: {}", event);
        // Здесь ваша бизнес-логика
    }

    private void handleFooUpdated(FooEvent event) {
//        log.info("Processing foo update: fooId={}, email={}",
//                event.getFooId(), event.getEmail());
        log.info("Processing FOO_CREATED event: {}", event);
        // Здесь ваша бизнес-логика
    }
}
