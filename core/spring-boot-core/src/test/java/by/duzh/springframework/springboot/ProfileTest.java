package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*@SpringBootTest(
        classes = {
                DefaultSpringBootConfiguration.class, ProfileTest.OracleConfig.class,
                ProfileTest.HsqlConfig.class, ProfileTest.JdbcConfig.class, ProfileTest.JpaConfig.class
        },
        properties = "spring.profiles.active=prod")*/

public class ProfileTest {

    @TestConfiguration
    @Profile("oracle")
    static class OracleConfig {
        @Bean
        public String baz() {
            return "Oracle Baz";
        }
    }

    @TestConfiguration
    @Profile("hsql")
    static class HsqlConfig {
        @Bean
        public String baz() {
            return "Hsql Baz";
        }
    }

    @TestConfiguration
    @Profile("jdbc")
    static class JdbcConfig {
        @Bean
        public String bar() {
            return "Jdbc Bar";
        }
    }

    @TestConfiguration
    @Profile("jpa")
    static class JpaConfig {
        @Bean
        public String bar() {
            return "Jpa Bar";
        }
    }

    private SpringApplication buildSpringApplication() {
        return new SpringApplicationBuilder()
                .sources(DefaultSpringBootConfiguration.class)
                .child(ProfileTest.OracleConfig.class, ProfileTest.HsqlConfig.class,
                        ProfileTest.JdbcConfig.class, ProfileTest.JpaConfig.class)
                .web(WebApplicationType.NONE)
                .build();
    }

    @Test
    void testProfileGroup() {
        var ctx = buildSpringApplication().run("--spring.profiles.active=prod");
        Arrays.stream(ctx.getEnvironment().getActiveProfiles()).forEach(System.out::println);

        assertEquals("Oracle Baz", ctx.getBean("baz", String.class));
        assertEquals("Jdbc Bar", ctx.getBean("bar", String.class));

        SpringApplication.exit(ctx);

        ctx = buildSpringApplication().run("--spring.profiles.active=dev");
        Arrays.stream(ctx.getEnvironment().getActiveProfiles()).forEach(System.out::println);

        assertEquals("Hsql Baz", ctx.getBean("baz", String.class));
        assertEquals("Jpa Bar", ctx.getBean("bar", String.class));

        SpringApplication.exit(ctx);
    }

}
