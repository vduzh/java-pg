package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class CommandLineRunnerTest {
    // run after application startup but before it starts accepting traffic
    @Component
    static class MyCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("MyCommandLineRunner is about started with args: " + Arrays.toString(args));
        }
    }

    @Test
    void test() {
        new SpringApplicationBuilder(DefaultSpringBootConfiguration.class)
                .child(MyCommandLineRunner.class)
                .web(WebApplicationType.NONE)
                .run("--foo=bar");
    }
}
