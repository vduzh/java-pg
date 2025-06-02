package by.duzh.springframework.springboot.context.properties;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, ConfigurationPropertiesTest.MyConfiguration.class})
public class ConfigurationPropertiesTest {

    @TestConfiguration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AcmeProperties.class)
    static class MyConfiguration {
    }

    @ConfigurationProperties(prefix = "acme")
    // @Setter - from lombook
    // @Getter - from lombook
    static class AcmeProperties {
        private String name;
        private int size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    @Test
    void testPrefix(@Autowired AcmeProperties config) {
        assertEquals("Learning Spring Boot", config.getName());
        assertEquals(10_000, config.getSize());
    }

    @Test
    void testClasspath() {
        System.out.println("Test not implemented: ConfigurationPropertiesTest.testClasspath");
    }

    @Test
    public void test() {
        System.out.println("Test not implemented: ConfigurationPropertiesTest");
    }
}
