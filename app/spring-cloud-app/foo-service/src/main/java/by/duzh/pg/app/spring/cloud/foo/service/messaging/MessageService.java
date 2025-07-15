package by.duzh.pg.app.spring.cloud.foo.service.messaging;

import by.duzh.pg.app.spring.cloud.foo.helper.MessageHelper;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final StreamBridge streamBridge;

    public void sendFooEventToRabbitMQ(String bindingName, FooEvent event) {
        sendFooEvent(bindingName, event);
    }

    public void sendFooEventToKafka(String bindingName, FooEvent event) {
        sendFooEvent(bindingName, event);
    }

    private void sendFooEvent(String bindingName, FooEvent event) {
        log.info("Sending message: {} with routing key: {}", event, event.getAction());

        boolean sent = streamBridge.send(bindingName, MessageHelper.buildFooMessage(event));

        if (sent) {
            log.info("Successfully sent message: {} with routing key: {}", event, event.getAction());
        } else {
            // TODO: how to handle error???
            log.error("Failed to send message: {}", event);
        }
    }
}