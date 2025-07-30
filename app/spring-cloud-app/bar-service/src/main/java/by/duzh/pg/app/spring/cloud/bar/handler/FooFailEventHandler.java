package by.duzh.pg.app.spring.cloud.bar.handler;

import by.vduzh.pg.event.action.dispatcher.ActionEventHandler;
import by.vduzh.pg.foo.dto.FooDto;
import by.vduzh.pg.foo.event.action.FooEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FooFailEventHandler implements ActionEventHandler<FooDto, FooEvent> {
    @Override
    public boolean supports(String action) {
        return action.equals("foo.logic-error");
    }

    @Override
    public void handle(FooEvent event) {
        log.debug("Processing FooEvent: action={}", event.getAction());

        var foo = event.getPayload();
        log.debug("Creating Foo: id={}, name={}", foo.id(), foo.name());

        // Здесь ваша бизнес-логика: add data to the db
        //throw new RuntimeException("Business logic error happened.");
    }
}
