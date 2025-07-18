package by.duzh.pg.app.spring.cloud.foo.helper;

import by.vduzh.pg.event.action.model.ActionEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MessageHelper {

    public static <T extends ActionEvent<?>> Message<T> buildMessage(T event) {
        return MessageBuilder.withPayload(event)
//                .setHeader("routingKey", event.getAction())
//                .setHeader("timestamp", Instant.now())
                .setHeader("source", "foo-service")
                .build();
    }
}
