package by.duzh.springframework.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChildTestConfiguration {
    @Bean
    public String bar() {
        return "bar";
    }
}
