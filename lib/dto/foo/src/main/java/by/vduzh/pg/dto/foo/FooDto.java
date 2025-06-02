package by.vduzh.pg.dto.foo;

import java.io.Serializable;

public record FooDto(Integer id, String name) implements Serializable {
}
