package by.duzh.pg.app.spring.cloud.bar.config;

import by.vduzh.pg.event.action.dispatcher.ActionEventDispatcher;
import by.vduzh.pg.event.action.model.ActionEvent;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.function.Consumer;

@Configuration
@ComponentScan(basePackageClasses = ActionEventDispatcher.class)
@Slf4j
@AllArgsConstructor
public class StreamConfig {

    private final ActionEventDispatcher dispatcher;

    /**
     * Produces a consumer bean is triggered based ion data (events) sent to the destination it is bound to.
     *
     * @return a Consumer<Message<FooEvent>> object.
     */
    @Bean
    // processUserCreatedEvent, processOrderCreatedEvent
    public Consumer<Message<FooEvent>> processFooEventFromRabbitMQ() {
        return message -> {
            log.debug("Precessing message from RabbitMQ: {}", message);
            processMessage(message);
        };
    }

    @Bean
    public Consumer<Message<FooEvent>> processFooEventFromKafka() {
        return message -> {
            log.debug("Precessing message from Kafka: {}", message);
            processMessage(message);
        };
    }

    private void processMessage(Message<? extends ActionEvent<?>> message) {
        MessageHeaders headers = message.getHeaders();
        log.debug("Headers: {}", headers);
        // process headers if necessary

        ActionEvent<?> event = message.getPayload();
        log.debug("ActionEvent: {}", event);
        // handle action event
        try {
            ActionEvent<?> actionEvent = message.getPayload();
            dispatcher.dispatch(actionEvent);
            log.debug("ActionEvent: {} has been processed", event.getAction());
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
            throw e; // Для повторной обработки
        }
    }
}
