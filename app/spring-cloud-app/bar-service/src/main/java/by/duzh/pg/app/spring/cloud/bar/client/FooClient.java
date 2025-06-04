package by.duzh.pg.app.spring.cloud.bar.client;

import by.vduzh.pg.dto.foo.FooDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// Если используется Eureka, можно убрать url, и Spring Cloud LoadBalancer найдёт сервис сам:
// @FeignClient(name = "foo-service", url = "http://localhost:8080")
@FeignClient(name = "foo-service")
public interface FooClient {

    // @GetMapping("/api/foos")
    @GetMapping("/foos")
    List<FooDto> getAll();
}