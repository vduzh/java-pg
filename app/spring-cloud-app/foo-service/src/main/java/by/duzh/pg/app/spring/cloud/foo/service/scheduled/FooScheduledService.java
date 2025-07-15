package by.duzh.pg.app.spring.cloud.foo.service.scheduled;

import by.duzh.pg.app.spring.cloud.foo.service.messaging.MessageService;
import by.vduzh.pg.foo.dto.FooDto;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FooScheduledService {
    private final MessageService messageService;

    @Scheduled(
            initialDelayString = "${scheduler.rabbitmq.initialDelay}",
            fixedDelayString = "${scheduler.rabbitmq.fixedDelay}"
    )
    public void sendMessageToRabbitMQ() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        FooDto payload = new FooDto(333, "Updated Foo from Code to RabbitMQ");
        var event = FooEvent.builder()
                .id(UUID.randomUUID().toString())
                .action("foo.updated")
                .payload(payload)
                .build();
        messageService.sendFooEventToRabbitMQ("fooEventToRabbitMQOut", event);

        stopWatch.stop();
        log.info("Task 'sendMessageToRabbitMQ' completed in {} milliseconds", stopWatch.getTotalTimeMillis());
    }

    @Scheduled(
            initialDelayString = "${scheduler.kafka.initialDelay}",
            fixedDelayString = "${scheduler.kafka.fixedDelay}"
    )
    public void sendMessageToKafka() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        FooDto payload = new FooDto(444, "Updated Foo from Code to Kafka");
        var event = FooEvent.builder()
                .id(UUID.randomUUID().toString())
                .action("foo.updated")
                .payload(payload)
                .build();
        messageService.sendFooEventToKafka("fooEventToKafkaOut", event);

        stopWatch.stop();
        log.info("Task 'sendMessageToKafka' completed in {} milliseconds", stopWatch.getTotalTimeMillis());
    }
}
