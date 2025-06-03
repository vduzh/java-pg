package by.duzh.pg.app.spring.cloud.bar.controller;

import by.vduzh.pg.dto.bar.BarDto;
import by.vduzh.pg.dto.foo.FooDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("bar")
public class BarController {

    private static final BarDto[] BAR_DATA = {
            new BarDto(1, "Bar 1"),
            new BarDto(2, "Bar 2")
    };

    private final WebClient.Builder webClientBuilder;

    public BarController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping
    public List<BarDto> getAll() {
        return List.of(BAR_DATA);
    }

    @GetMapping("/{id}")
    public BarDto getBar(@PathVariable int id) {
        return Arrays.stream(BAR_DATA)
                .filter(fooDto -> fooDto.id() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Use logical name of the "foo-service" instead of IP:Port
     * Spring Cloud LoadBalancer, which is integrated with Eureka, will find an available instance
     * and process a request.
     */
    @GetMapping("/foo")
    public List<FooDto> getAllFoo() {
        return webClientBuilder.build()
                .get()
                .uri("http://foo-service/foo")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FooDto>>() {
                })
                .block();
    }
}
