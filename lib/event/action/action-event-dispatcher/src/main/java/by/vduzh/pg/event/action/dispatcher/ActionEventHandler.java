package by.vduzh.pg.event.action.dispatcher;

import by.vduzh.pg.event.action.model.ActionEvent;

public interface ActionEventHandler<T, E extends ActionEvent<T>> {
    boolean supports(String action);

    void handle(E event);
}
