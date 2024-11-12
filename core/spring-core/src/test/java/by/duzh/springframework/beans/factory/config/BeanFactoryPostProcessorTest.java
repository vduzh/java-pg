package by.duzh.springframework.beans.factory.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(BeanFactoryPostProcessorTest.AppConfig.class)
public class BeanFactoryPostProcessorTest {
    public static class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // set lazy to true in the foo bean
            Arrays.stream(beanFactory.getBeanDefinitionNames())
                    .filter(name -> name.equals("foo"))
                    .map(beanFactory::getBeanDefinition)
                    .forEach(beanDefinition -> beanDefinition.setLazyInit(true));
        }
    }

    @Configuration
    public static class AppConfig {
        @Bean
        public static CustomBeanFactoryPostProcessor customBeanFactoryPostProcessor() {
            return new CustomBeanFactoryPostProcessor();
        }

        @Bean
        public String foo() {
            return "foo";
        }
    }

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    void test() throws Exception {
        assertTrue(ctx.getBeanDefinition("foo").isLazyInit());
    }
}
