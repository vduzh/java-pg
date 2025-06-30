package by.duzh.pg.app.spring.cloud.bar.client.feign;

import by.vduzh.pg.foo.dto.FooDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Если используется Eureka, можно убрать url, и Spring Cloud LoadBalancer найдёт сервис сам:
// @FeignClient(name = "foo-service", url = "http://localhost:8080")
@FeignClient(name = "foo-service", fallback = FooFeignClientFallback.class)
public interface FooClient {

    @GetMapping("/foos")
    List<FooDto> getAll();

    @GetMapping("/foos/{id}")
    public FooDto getFoo(@PathVariable int id);
}

@Component
class FooFeignClientFallback implements FooClient {
    @Override
    public List<FooDto> getAll() {
        return List.of();
    }

    @Override
    public FooDto getFoo(int id) {
        // or return null;
        return new FooDto(-1, "I am a fallback of of the foo with id:" + id);
    }
}