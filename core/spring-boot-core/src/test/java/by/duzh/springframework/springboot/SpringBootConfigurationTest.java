package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SpringBootConfigurationTest.PrimaryConfig.class})
public class SpringBootConfigurationTest {

    @SpringBootConfiguration
    static class PrimaryConfig {
        @Bean
        public String test() {
            return "test";
        }
    }

    @Autowired
    String test;

    @Test
    void name() {
        assertEquals("test", test);
    }
}
