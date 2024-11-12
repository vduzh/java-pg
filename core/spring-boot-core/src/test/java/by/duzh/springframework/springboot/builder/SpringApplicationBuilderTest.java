package by.duzh.springframework.springboot.builder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpringApplicationBuilderTest {
    @Configuration
    static class ParentConfig {
        @Bean
        public String foo() {return "foo";}
    }

    @Configuration
    static class ChildConfig {
        @Bean
        public String bar() {return "bar";}
    }

    @Test
    void testRun() {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
                .sources(ParentConfig.class)
                .child(ChildConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run();

        assertEquals("foo", ctx.getBean("foo", String.class));
        assertEquals("bar", ctx.getBean("bar", String.class));
    }

    @Test
    void testBuildAndRun() {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(ParentConfig.class)
                .child(ChildConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .build();

        application.run();
    }
}
