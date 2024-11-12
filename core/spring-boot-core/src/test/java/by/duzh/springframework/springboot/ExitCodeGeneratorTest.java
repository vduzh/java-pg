package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExitCodeGeneratorTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ExitCodeGenerator exitCodeGenerator() {
            return () -> 10;
        }
    }

    @Test
    void exit() {
        var ctx = new SpringApplicationBuilder()
                .sources(DefaultSpringBootConfiguration.class)
                .child(TestConfig.class)
                .web(WebApplicationType.NONE)
                .run();

        assertEquals(10, SpringApplication.exit(ctx));
    }

    @Test
    void exitWithGenerator() {
        var ctx = new SpringApplicationBuilder()
                .sources(DefaultSpringBootConfiguration.class)
                .web(WebApplicationType.NONE)
                .run();

        assertEquals(100, SpringApplication.exit(ctx, () -> 100));
    }
}
