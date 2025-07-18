package by.duzh.pg.app.spring.cloud.foo.service.messaging;

import by.duzh.pg.app.spring.cloud.foo.helper.MessageHelper;
import by.vduzh.pg.event.action.model.ActionEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final StreamBridge streamBridge;

    public void sendActionEvent(String bindingName, ActionEvent<?> event) {
        log.info("Sending message: {} with routing key: {}", event, event.getAction());

        // брокер недоступен, очередь переполнена, неправильная конфигурация
        boolean sent = streamBridge.send(bindingName, MessageHelper.buildMessage(event));

        if (!sent) {
            throw new MessageDeliveryException(
                    "Failed to send message to binding '%s'. Event: %s, Action: %s, Timestamp: %s".formatted(
                            bindingName, event, event.getAction(), java.time.Instant.now())
            );
        }

        log.debug("Successfully sent message: {} with routing key: {}", event, event.getAction());
    }
}