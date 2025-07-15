package by.vduzh.pg.event.action.dispatcher;

import by.vduzh.pg.event.action.model.ActionEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActionEventDispatcher {
    private final List<? extends ActionEventHandler<?, ?>> handlers;

    public ActionEventDispatcher(List<? extends ActionEventHandler<?, ?>> handlers) {
        this.handlers = handlers;
    }

    @SuppressWarnings("unchecked")
    public void dispatch(ActionEvent<?> event) {
        var actionHandler = handlers.stream()
                .filter(handler -> handler.supports(event.getAction()))
                .findFirst()
                .orElseThrow(() -> new HandlerNotFoundException("There is no handler for action: "
                        + event.getAction())
                );

        ((ActionEventHandler<Object, ActionEvent<Object>>) actionHandler)
                .handle((ActionEvent<Object>) event);
    }
}
