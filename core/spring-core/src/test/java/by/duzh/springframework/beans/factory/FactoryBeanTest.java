package by.duzh.springframework.beans.factory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

// FactoryBean used to create a ben of object that can not be created with Spring's standard approach
//  - JNDI, etc.

public class FactoryBeanTest {
    public static class Foo {
    }

    public static class FooFactoryBean implements FactoryBean<Foo> {
        @Override
        public Foo getObject() throws Exception {
            return new Foo();
        }

        @Override
        public Class<?> getObjectType() {
            return Foo.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }
    }

    public static class ThirdPartyFooFactory {
        public static Foo create() {
            return new Foo();
        }
    }

    @Configuration
    public static class TestConfig {
        @Bean // Note: FooFactoryBean is used, but getBean returns Foo
        public FooFactoryBean foo() {
            return new FooFactoryBean();
        }
    }

    @Test
    void getObject() throws Exception {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class)) {
            Foo foo = ctx.getBean("foo", Foo.class);
        }
    }

    @Test
    void getBeanFactory() throws Exception {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class)) {
            // not recommended!!!
            FooFactoryBean factory = ctx.getBean("&foo", FooFactoryBean.class);
        }
    }

    @Test
    void factoryMethod() throws Exception {
        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext();) {
            context.load("classpath:context/app-context-factory-bean.xml");
            context.refresh();

            Foo foo = context.getBean("foo", Foo.class);
        }
    }
}
