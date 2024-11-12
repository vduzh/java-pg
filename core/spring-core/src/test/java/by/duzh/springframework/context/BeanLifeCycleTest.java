package by.duzh.springframework.context;

import by.duzh.springframework.context.beans.AnnotatedInitMethodCallbackBean;
import by.duzh.springframework.context.beans.InitMethodCallbackBean;
import by.duzh.springframework.context.beans.InitializingBeanCallbackBean;
import by.duzh.springframework.context.beans.MultipleInitMethodsCallbackBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeanLifeCycleTest {
    private GenericXmlApplicationContext buildGenericXmlApplicationContext() throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("classpath:context/app-context-init-method.xml");
        context.refresh();
        return context;
    }

    @Test
    public void initMethods() throws Exception {
        try (GenericXmlApplicationContext context = buildGenericXmlApplicationContext()) {
            // the init method is successful
            var success = context.getBean("initMethodSuccess", InitMethodCallbackBean.class);
            assertEquals("1", success.getName());
            assertEquals("11", success.getValue());

            // default value set in the init method
            var def = context.getBean("initMethodDefault", InitMethodCallbackBean.class);
            assertEquals("default", def.getValue());

            // the init method fails
            assertThrows(BeanCreationException.class, () -> {
                context.getBean("initMethodFailure");
            });
        }
    }

    @Test
    public void initializingBean() throws Exception {
        try (GenericXmlApplicationContext context = buildGenericXmlApplicationContext()) {

            // the afterPropertiesSet method is successful
            var success = context.getBean("initializingBeanSuccess", InitializingBeanCallbackBean.class);
            assertEquals("2", success.getName());
            assertEquals("22", success.getValue());

            // default value set in the afterPropertiesSet method
            var def = context.getBean("initializingBeanDefault", InitializingBeanCallbackBean.class);
            assertEquals("default2", def.getValue());

            // the init method fails
            assertThrows(BeanCreationException.class, () -> {
                context.getBean("initializingBeanFailure");
            });
        }
    }

    @Test
    public void postContract() throws Exception {
        try (GenericXmlApplicationContext context = buildGenericXmlApplicationContext()) {

            // the init method is successful
            var success = context.getBean("postContractSuccess", AnnotatedInitMethodCallbackBean.class);
            assertEquals("3", success.getName());
            assertEquals("33", success.getValue());

            // default value set in the init method
            var def = context.getBean("postContractDefault", AnnotatedInitMethodCallbackBean.class);
            assertEquals("default3", def.getValue());

            // the init method fails
            assertThrows(BeanCreationException.class, () -> {
                context.getBean("postContractFailure");
            });
        }
    }

    @Test
    public void javaConfigInitMethod() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class)) {
            var success = context.getBean("javaConfigSuccess", InitMethodCallbackBean.class);
            assertEquals("4", success.getName());
            assertEquals("44", success.getValue());

            // default value set in the init method
            var def = context.getBean("javaConfigDefault", InitMethodCallbackBean.class);
            assertEquals("default", def.getValue());

            // the init method fails
            assertThrows(BeanCreationException.class, () -> {
                context.getBean("javaConfigFailure");
            });
        }
    }

    @Test
    public void multipleInitMethods() throws Exception {
        try (GenericXmlApplicationContext context = buildGenericXmlApplicationContext()) {
            var success = context.getBean("multipleInitMethodsSuccess", MultipleInitMethodsCallbackBean.class);
            assertEquals("123", success.getValue());
        }
    }

    @Test
    public void registerShutdownHook() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        context.registerShutdownHook();
    }

    @Configuration
    public static class LifeCycleConfig {
        @Bean(initMethod = "init", destroyMethod="destroy")
        public InitMethodCallbackBean javaConfigSuccess() {
            InitMethodCallbackBean bean = new InitMethodCallbackBean();
            bean.setName("4");
            bean.setValue("44");
            return bean;
        }

        @Bean(initMethod = "init", destroyMethod="destroy")
        public InitMethodCallbackBean javaConfigDefault() {
            InitMethodCallbackBean bean = new InitMethodCallbackBean();
            bean.setName("4");
            return bean;
        }

        @Lazy
        @Bean(initMethod = "init", destroyMethod="destroy")
        public InitMethodCallbackBean javaConfigFailure() {
            return new InitMethodCallbackBean();
        }
    }
}