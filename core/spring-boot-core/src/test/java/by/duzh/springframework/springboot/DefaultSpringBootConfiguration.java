package by.duzh.springframework.springboot;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
//Note: @EnableAutoConfiguration - no need here!
public class DefaultSpringBootConfiguration {
    @Bean
    public String foo() {
        return "foo";
    }
}