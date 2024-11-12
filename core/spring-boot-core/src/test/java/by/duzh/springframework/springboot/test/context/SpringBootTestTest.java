package by.duzh.springframework.springboot.test.context;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import by.duzh.springframework.springboot.beans.Bar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        // Primary Configuration
        DefaultSpringBootConfiguration.class,
        // Test Configuration
        SpringBootTestTest.TestConfig.class},
        args = "--app.test=one"
)
public class SpringBootTestTest {

    @TestConfiguration
    @ComponentScan(useDefaultFilters = false,
            basePackages = "by.duzh.springframework.springboot.beans",
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Bar.class)
    )
    static class TestConfig {
        @Bean
        public String buz() {
            return "buz";
        }
    }

    // from the PRIMARY config
    @Autowired
    String foo;

    // from the INNER text config
    @Autowired
    String buz;

    // Scanned component as param
    @Test
    void testClasses(@Autowired Bar bar) {
        //Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        assertEquals("foo", foo);
        assertEquals("buz", buz);
        assertEquals("bar", bar.value());
    }
}
