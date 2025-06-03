package by.duzh.pg.app.spring.cloud.foo.controller;

import by.vduzh.pg.dto.foo.FooDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("foo")
public class FooController {

    private static final FooDto[] FOO_DATA = {
            new FooDto(1, "Foo 1"),
            new FooDto(2, "Foo 2")
    };

    @GetMapping
    public List<FooDto> getAll() {
        return List.of(FOO_DATA);
    }

    @GetMapping("/{id}")
    public FooDto getFoo(@PathVariable int id) {
        return Arrays.stream(FOO_DATA)
                .filter(fooDto -> fooDto.id() == id)
                .findFirst()
                .orElse(null);
    }
}
