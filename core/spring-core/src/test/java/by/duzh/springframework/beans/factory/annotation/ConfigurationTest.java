package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringJUnitConfig(ConfigurationTest.AppConfig.class)
public class ConfigurationTest {

    @Configuration
    public static class AppConfig { // can not be finale as it is proxied with CGBLib
        @Bean // Like <bean> in xml config file
        public Integer age()  /* age is like id of the bean tag */ {
            return 18;
        }

        @Bean // any object expose like as Spring Bean
        public Calendar calendar() {  // can not be finale as it is proxied with CGBLib
            return GregorianCalendar.getInstance();
        }
    }

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private AppConfig appConfig;

    @Test
    void test() throws Exception {
        Assertions.assertTrue(appConfig.getClass().getName().contains("Enhancer")); // check for CGBLib

        Assertions.assertEquals(18, ctx.getBean("age", Integer.class).intValue());
        ctx.getBean(Calendar.class);
    }
}
