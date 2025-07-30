package by.duzh.pg.app.spring.cloud.bar.config;

import by.vduzh.pg.event.action.dispatcher.ActionEventDispatcher;
import by.vduzh.pg.event.action.model.ActionEvent;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.kafka.KafkaListenerContainerCustomizer;
import org.springframework.cloud.stream.binder.kafka.properties.KafkaConsumerProperties;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.backoff.FixedBackOff;

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

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<?, ?> template) {
        DefaultErrorHandler handler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template),
                new FixedBackOff(1000L, 1)
        );
        handler.addRetryableExceptions(Exception.class);
        return handler;
    }

//    @Bean
//    public ListenerContainerCustomizer<AbstractMessageListenerContainer<byte[], byte[]>> customizer(DefaultErrorHandler errorHandler) {
//        return (container, dest, group) -> {
//            container.setCommonErrorHandler(errorHandler);
//        };
//    }

    @Bean
    public KafkaListenerContainerCustomizer kafkaListenerContainerCustomizer(
            DefaultErrorHandler errorHandler) {
        return (container, type, id) -> {
            container.setCommonErrorHandler(errorHandler);
        };
    }

    /**
     * String payload comes instead of FooEvent.
     * </p>
     *
     * 3 delivery attempts will happen and error will be logged in.
     */
    @Bean
//    public Consumer<Message<FooEvent>> deserializationError() {
    public Consumer<Message<String>> deserializationError() {
        return message -> {
            //log.debug("deserializationError started");
            // trigger error just accessing to payload class
            //message.getPayload().getClass();
            log.debug("deserializationError payload: {}", message.getPayload());

            throw new RuntimeException("Trigger deserializationError!!");
        };
    }

//    @Bean
//    public Consumer<ErrorMessage> myErrorHandler() {
//        return message -> {
//            log.debug("myErrorHandler started : {}", message);
//        };
//    }

    //    @Bean
//    public Consumer<Message<FooEvent>> businessLogicError() {
//        // TODO: send invalid action, foo.unknown
//        return message -> {
//            log.debug("businessLogicError expected: {}", message);
//            processMessage(message);
//        };
//    }

    //    @Bean
//    public Consumer<Message<FooEvent>> brokerError() {
//        // TODO: don't know how to trigger
//        return message -> {
//            log.debug("brokerError expected: {}", message);
//            processMessage(message);
//        };
//    }

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
