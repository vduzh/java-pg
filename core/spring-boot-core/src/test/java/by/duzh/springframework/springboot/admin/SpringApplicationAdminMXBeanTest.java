package by.duzh.springframework.springboot.admin;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.admin.SpringApplicationAdminMXBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;

// Note: Have a look at https://www.baeldung.com/spring-boot-admin

//@SpringBootTest(args = "--spring.application.admin.enabled=true")
@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, SpringApplicationAdminMXBeanTest.TestConfig.class})
public class SpringApplicationAdminMXBeanTest {

    /*@EnableAdminServer*/
    @TestConfiguration
    @EnableAutoConfiguration
    static class TestConfig {

    }

    @Test
    void name() {
/*
        var ctx = new SpringApplicationBuilder()
                .sources(DefaultSpringBootConfiguration.class)
                .child(TestConfig.class)
                .web(WebApplicationType.REACTIVE)
                .run("--spring.application.admin.enabled=true");

        System.out.println(ctx.getBean(SpringApplicationAdminMXBean.class));
*/
    }

    @Test
    void test(@Autowired ApplicationContext ctx) {
        System.out.println(ctx.getBean(SpringApplicationAdminMXBean.class));
    }
}
