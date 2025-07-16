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
        messageService.sendFooEventToRabbitMQ("fooEventToRabbitMQOut",
                new FooEvent(UUID.randomUUID().toString(), "foo.updated", payload));

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
        messageService.sendFooEventToKafka("fooEventToKafkaOut",
                new FooEvent(UUID.randomUUID().toString(), "foo.updated", payload));

        stopWatch.stop();
        log.info("Task 'sendMessageToKafka' completed in {} milliseconds", stopWatch.getTotalTimeMillis());
    }
}
