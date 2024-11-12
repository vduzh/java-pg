package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

public class ApplicationRunnerTest {

    // run after application startup but before it starts accepting traffic
    @Component
    @Order(2)
    static class MyApplicationRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("MyApplicationRunner is about started with args: " + args.getOptionNames());
        }
    }

    @Component
    @Order(1)
    static class MyApplicationRunner2 implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("MyApplicationRunner2 is about started with args: " + args.getOptionNames());
        }
    }

    @Test
    void test() {
        new SpringApplicationBuilder(DefaultSpringBootConfiguration.class)
                .child(MyApplicationRunner.class)
                .web(WebApplicationType.NONE)
                .run("--foo=bar");
    }

    @Test
    void testOrder() {
        new SpringApplicationBuilder(DefaultSpringBootConfiguration.class)
                .child(MyApplicationRunner2.class, MyApplicationRunner.class)
                .web(WebApplicationType.NONE)
                .run();
    }
}
