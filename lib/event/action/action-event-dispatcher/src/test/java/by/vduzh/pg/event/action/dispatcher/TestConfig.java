package by.vduzh.pg.event.action.dispatcher;

import by.vduzh.pg.event.action.model.ActionEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TestConfig {
    @Bean
    public ActionEventDispatcher dispatcher(List<ActionEventHandler<?, ActionEvent<?>>> handlers) {
        return new ActionEventDispatcher(handlers);
    }
}
