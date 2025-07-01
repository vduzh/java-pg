package by.duzh.pg.app.spring.cloud.foo.config;

import by.vduzh.pg.customer.event.FooEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Configuration
public class StreamConfig {

    @Bean
    public Supplier<Flux<Message<FooEvent>>> sendMessage() {
        // Будем использовать StreamBridge для отправки
        return Flux::empty;
    }
}
