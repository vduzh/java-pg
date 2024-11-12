package by.duzh.springframework.springboot.autoconfigure;

import by.duzh.springframework.springboot.beans.Bar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class SpringBootApplicationTest {

    @SpringBootApplication(scanBasePackageClasses = Bar.class)
    static class AppConfig {
        public String str() {
            return "strValue";
        }
    }

    @Test
    void test() {
        var ctx = SpringApplication.run(AppConfig.class);
    }
}
