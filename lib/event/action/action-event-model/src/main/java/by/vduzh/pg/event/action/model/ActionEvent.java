package by.vduzh.pg.event.action.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ActionEvent<T> {

    private String id;

    private String action;

    private T payload;

    @Override
    public String toString() {
        return "%s(id=%s, action=%s, payload=%s)".formatted(getClass().getSimpleName(), id, action, payload);
    }
}

