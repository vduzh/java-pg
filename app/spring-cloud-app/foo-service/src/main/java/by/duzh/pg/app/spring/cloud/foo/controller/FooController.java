package by.duzh.pg.app.spring.cloud.foo.controller;

import by.vduzh.pg.dto.foo.FooDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/foos")
public class FooController {

    @Autowired
    private Environment env;

    private static final FooDto[] FOO_DATA = {
            new FooDto(1, "Foo 1"),
            new FooDto(2, "Foo 2")
    };

    @GetMapping("/test")
    public String test() {
        return getClass() + "is working...";
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        var res = new LinkedHashMap<String, Object>();

        res.put("app.title", env.getProperty("app.title"));
        res.put("app.timeout", env.getProperty("app.timeout"));
        res.put("app.db.host", env.getProperty("app.db.host"));
        res.put("app.db.user", env.getProperty("app.db.user"));
        res.put("app.db.password", env.getProperty("app.db.password"));

        res.put("foo.title", env.getProperty("foo.title"));
        res.put("foo.db.url", env.getProperty("foo.db.url"));

        return res;
    }

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
