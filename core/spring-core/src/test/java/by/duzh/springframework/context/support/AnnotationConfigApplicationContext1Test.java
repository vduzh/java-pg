package by.duzh.springframework.context.support;

import by.duzh.springframework.context.beans.SomeBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationConfigApplicationContext1Test {
    private AnnotationConfigApplicationContext ctx;

    @Configuration
    public static class AppConfig {
        @Bean
        public String foo() {
            return "foo";
        }
    }

    @BeforeAll
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Service
    public static class SomeService {
    }

    @Test
    void register() throws Exception {
        ctx.register(SomeService.class);

        ctx.getBean(SomeService.class);
        ctx.getBean("foo");
    }

    @Test
    void registerBean() throws Exception {
        //ctx.registerBean();
    }

    @Test
    void scan() throws Exception {
        ctx.scan("by.duzh.springframework.context.beans");

        ctx.getBean("foo"); // an old bean
        ctx.getBean(SomeBean.class);  // a scanned bean
    }

    @Test
    void setBeanNameGenerator() throws Exception {
        AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator() {
            @Override
            public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
                return super.generateBeanName(definition, registry) + "2";
            }
        };
        ctx.setBeanNameGenerator(generator); // affects register and scan
        ctx.register(SomeService.class);
        ctx.scan("by.duzh.springframework.context.beans");

        ctx.getBean("annotationConfigApplicationContextTest.SomeService2");
        ctx.getBean("someBean2");
        // But:
        ctx.getBean("foo");
    }

    @Test
    void setEnvironment() throws Exception {
        // TODO: process
        ConfigurableEnvironment env = null;
        //ctx.setEnvironment();
    }

    @Test
    void setScopeMetadataResolver() throws Exception {
        // TODO: process
        ScopeMetadataResolver scopeMetadataResolver = null;

        //ctx.setScopeMetadataResolver();
    }
}

