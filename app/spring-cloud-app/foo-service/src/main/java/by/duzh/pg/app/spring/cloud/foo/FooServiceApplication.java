package by.duzh.pg.app.spring.cloud.foo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FooServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FooServiceApplication.class, args);
    }
}
