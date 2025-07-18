package by.duzh.pg.app.spring.cloud.foo.service.scheduled;

import by.duzh.pg.app.spring.cloud.foo.service.messaging.MessageService;
import by.vduzh.pg.event.action.model.ActionEvent;
import by.vduzh.pg.foo.dto.FooDto;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import static by.duzh.pg.app.spring.cloud.foo.util.MessageUtils.messageId;

@Service
@Slf4j
@RequiredArgsConstructor
public class FooScheduledService {
    public static final String FOO_EVENT_TO_RABBIT_MQ_OUT = "fooEventToRabbitMQOut";
    public static final String FOO_EVENT_TO_KAFKA_OUT = "fooEventToKafkaOut";
    public static final String INCORRECT_EVENT_TYPE_OUT = "incorrectEventTypeOut";

    private final MessageService messageService;

    @Scheduled(initialDelayString = "${scheduler.rabbitmq.initialDelay}",
            fixedDelayString = "${scheduler.rabbitmq.fixedDelay}")
    public void sendMessageToRabbitMQ() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        FooDto payload = new FooDto(333, "Updated Foo from Code to RabbitMQ");
        FooEvent event = new FooEvent(messageId(), "foo.updated", payload);

        try {
            messageService.sendActionEvent(FOO_EVENT_TO_RABBIT_MQ_OUT, event);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        stopWatch.stop();

        //log.debug("Task 'sendMessageToRabbitMQ' completed in {} milliseconds", stopWatch.getTotalTimeMillis());
    }

    @Scheduled(initialDelayString = "${scheduler.kafka.initialDelay}",
            fixedDelayString = "${scheduler.kafka.fixedDelay}")
    public void sendMessageToKafka() {
        FooDto payload = new FooDto(444, "Updated Foo from Code to Kafka");
        FooEvent event = new FooEvent(messageId(), "foo.updated", payload);

        try {
            messageService.sendActionEvent(FOO_EVENT_TO_KAFKA_OUT, event);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(initialDelayString = "${scheduler.kafka.initialDelay}",
            fixedDelayString = "${scheduler.kafka.fixedDelay}")
    public void sendMessageWithWrongPayloadTypeToKafka() {
        var event = new ActionEvent<String>(messageId(), "foo.created", "dummy");
        try {
            messageService.sendActionEvent(INCORRECT_EVENT_TYPE_OUT, event);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(initialDelayString = "${scheduler.kafka.initialDelay}",
            fixedDelayString = "${scheduler.kafka.fixedDelay}")
    public void sendMessageWithLogicErrorToKafka() {
        FooDto payload = new FooDto(444, "Logic Error from Code to Kafka");
        FooEvent event = new FooEvent(messageId(), "foo.logic-error", payload);

        try {
            messageService.sendActionEvent(FOO_EVENT_TO_KAFKA_OUT, event);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
