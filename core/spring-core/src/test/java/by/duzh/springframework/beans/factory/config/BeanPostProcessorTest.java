package by.duzh.springframework.beans.factory.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(BeanPostProcessorTest.AppConfig.class)
public class BeanPostProcessorTest {
    static class CustomBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (beanName.equals("foo")) {
                ((StringBuilder) bean).append("!");
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (beanName.equals("foo")) {
                ((StringBuilder) bean).append("?");
            }
            return bean;
        }
    }

    @Configuration
    static class AppConfig {
        @Bean
        public static CustomBeanPostProcessor customBeanPostProcessor() {
            return new CustomBeanPostProcessor();
        }

        @Bean
        public StringBuilder foo() {
            return new StringBuilder("bar");
        }
    }

    @Autowired
    private StringBuilder sb;

    @Test
    void name() throws Exception {
        assertEquals("bar!?", sb.toString());
    }
}