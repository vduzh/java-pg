package by.vduzh.pg.foo.event.action;

import by.vduzh.pg.event.action.model.ActionEvent;
import by.vduzh.pg.foo.dto.FooDto;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public class FooEvent extends ActionEvent<FooDto> {
}