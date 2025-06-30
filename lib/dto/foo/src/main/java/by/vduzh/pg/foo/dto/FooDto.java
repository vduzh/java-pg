package by.vduzh.pg.foo.dto;

import java.io.Serializable;

public record FooDto(Integer id, String name) implements Serializable {
}
