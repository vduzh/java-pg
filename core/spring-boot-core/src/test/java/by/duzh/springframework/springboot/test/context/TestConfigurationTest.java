package by.duzh.springframework.springboot.test.context;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, TestConfigurationTest.TestConfig.class})
public class TestConfigurationTest {

    // customize the primary configuration in the PrimaryTestConfig.class
    @TestConfiguration
    static class TestConfig {
        @Bean
        public String buz() {
            return "buz";
        }
    }

    @Disabled
    @Test
    void test(@Autowired String foo, @Autowired String buz) {
        assertEquals("foo", foo);
        assertEquals("buz", buz);
    }
}
