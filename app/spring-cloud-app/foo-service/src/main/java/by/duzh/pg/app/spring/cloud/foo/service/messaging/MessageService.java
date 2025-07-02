package by.duzh.pg.app.spring.cloud.foo.service.messaging;

import by.vduzh.pg.customer.event.FooEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final StreamBridge streamBridge;

    public void sendFooEvent(FooEvent event, String routingKey) {
        log.info("Sending message: {} with routing key: {}", event, routingKey);

        // Build a message containing FooEvent object
        Message<FooEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader("routingKey", routingKey)
//                .setHeader("timestamp", Instant.now())
                .setHeader("source", "foo-service")
                .build();

        // Send a message
        boolean sent = streamBridge.send("sendFooEvent-out-0", message);
        if (sent) {
            log.info("Successfully sent message: {} with routing key: {}", event, routingKey);
        } else {
            // TODO: how to handle error???
            log.error("Failed to send message: {}", event);
        }
    }
}
