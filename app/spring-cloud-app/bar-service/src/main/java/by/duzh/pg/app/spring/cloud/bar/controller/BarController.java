package by.duzh.pg.app.spring.cloud.bar.controller;

import by.duzh.pg.app.spring.cloud.bar.client.feign.FooClient;
import by.vduzh.pg.bar.dto.BarDto;
import by.vduzh.pg.foo.dto.FooDto;
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
@RequestMapping("bars")
public class BarController {

    private static final BarDto[] BAR_DATA = {
            new BarDto(1, "Bar 1"),
            new BarDto(2, "Bar 2")
    };

    private final String instanceId;
    private final FooClient fooClient;

    public BarController(FooClient fooClient,
                         @Value("${eureka.instance.instance-id}") String instanceId) {
        this.fooClient = fooClient;
        this.instanceId = instanceId;
    }

    private void logInstance() {
        log.info("Current instance: {}", instanceId);
    }

    @GetMapping
    public List<BarDto> getAll() {
        logInstance();

        return List.of(BAR_DATA);
    }

    @GetMapping("/{id}")
    public BarDto getBar(@PathVariable int id) {
        logInstance();

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
    @GetMapping("/foos")
    public List<FooDto> getAllFoo() {
        logInstance();
        return fooClient.getAll();
    }

    @GetMapping("/foos/{id}")
    public FooDto getAllFoo(@PathVariable int id) {
        logInstance();
        return fooClient.getFoo(id);
    }
}
