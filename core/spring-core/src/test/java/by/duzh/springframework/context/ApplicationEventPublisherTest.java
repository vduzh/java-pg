package by.duzh.springframework.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationEventPublisherTest {
    // Application event
    public static class CustomEvent extends ApplicationEvent {
        private final String data;

        public CustomEvent(Object source, String data) {
            super(source);
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    // Application event listener
    @Component("customEventListenerBean")
    public static class CustomEventListenerBean implements ApplicationListener<CustomEvent> {
        @Override
        public void onApplicationEvent(CustomEvent event) {
            System.out.println("Received: " + event.getData());
        }
    }

    // Event publisher
    @Component("customEventPublisherBean")
    public static class CustomEventPublisherBean extends ApplicationObjectSupport {
        public void publish(String data) {
            getApplicationContext().publishEvent(new CustomEvent(this, data));
        }
    }

    //@Configuration - there is no @Bean methods
    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CustomEventListenerBean.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CustomEventPublisherBean.class)
            }
    )
    public static class AppConfig {
    }

    private ApplicationEventPublisher eventPublisher;

    @BeforeAll
    void setUp() {
        eventPublisher = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void test() throws Exception {
        CustomEventPublisherBean publisherBean = ((ApplicationContext) eventPublisher).getBean(CustomEventPublisherBean.class);
        publisherBean.publish("foo");
    }
}
