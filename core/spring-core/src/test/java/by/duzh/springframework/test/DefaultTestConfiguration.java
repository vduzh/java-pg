package by.duzh.springframework.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultTestConfiguration {
    @Bean
    public String foo() {
        return "foo";
    }
}
