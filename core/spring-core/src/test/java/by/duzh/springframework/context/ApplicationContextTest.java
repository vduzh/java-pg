package by.duzh.springframework.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationContextTest {
    private ApplicationContext ctx;

    public static class AppEvent extends ApplicationEvent {
        private String data;

        public AppEvent(Object source, String data) {
            super(source);
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    @Configuration
    public static class AppConfig {
        @Bean
        public String title() {
            return "Mr.";
        }

        @Bean
        public ResourceBundleMessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("by/duzh/springframework/context/users");
            return messageSource;
        }

        @Bean
        public ApplicationListener<AppEvent> consumer() {
            return new ApplicationListener<AppEvent>() {
                @Override
                public void onApplicationEvent(AppEvent event) {
                    System.out.println("AppEvent: " + event.getData());
                }
            };
        }
    }

    @BeforeAll
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void getId() {
    }

    @Test
    void getApplicationName() throws Exception {
        assertTrue(ctx.getApplicationName().isEmpty());
    }

    @Test
    public void getDisplayName() throws Exception {
        // org.springframework.context.annotation.AnnotationConfigApplicationContext@50b494a6
        //assertTrue(context.getDisplayName().isEmpty());
    }

    @Test
    public void getStartupDate() throws Exception {
        assertTrue(ctx.getStartupDate() > 0);
    }

    @Configuration
    public static class ParentAppConfig {
        @Bean // under name greeting
        public String greeting() {
            return "Hello!";
        }
    }

    @Test
    public void getParent() throws Exception {
        ApplicationContext parent = new AnnotationConfigApplicationContext(ParentAppConfig.class);
        ((AnnotationConfigApplicationContext) ctx).setParent(parent);

        assertEquals(parent, ctx.getParent());

        ctx.getBean("greeting"); // parent bean
        ctx.getBean("title"); // child bean
    }

    @Test
    public void getAutowireCapableBeanFactory() throws Exception {
        //NOTE: look into the doc. It seems for internal usage...

        //System.out.println(context.getAutowireCapableBeanFactory());

        //AutowireCapableBeanFactory
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("Jack", ctx.getMessage("user1", null, Locale.getDefault()));
    }

    @Test
    public void publishEvent() throws Exception {
        ctx.publishEvent(new AppEvent(this, "foo"));
    }
}