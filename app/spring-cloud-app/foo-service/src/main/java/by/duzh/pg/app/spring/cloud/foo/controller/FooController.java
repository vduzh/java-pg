package by.duzh.pg.app.spring.cloud.foo.controller;

import by.vduzh.pg.dto.foo.FooDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/foos")
public class FooController {

    private static final FooDto[] FOO_DATA = {
            new FooDto(1, "Foo 1"),
            new FooDto(2, "Foo 2")
    };

    private final String instanceId;

    public FooController(@Value("${eureka.instance.instance-id}") String instanceId) {
        this.instanceId = instanceId;
    }

    @GetMapping("/test")
    public String test() {
        logInstance();

        return getClass() + "is working...";
    }

    @GetMapping
    public List<FooDto> getAll() {
        logInstance();

        return List.of(FOO_DATA);
    }

    @GetMapping("/{id}")
    public FooDto getFoo(@PathVariable int id) {
        logInstance();

        return Arrays.stream(FOO_DATA)
                .filter(fooDto -> fooDto.id() == id)
                .findFirst()
                .orElse(null);
    }

    private void logInstance() {
        log.info("Current instance: {}", instanceId);
    }
}
