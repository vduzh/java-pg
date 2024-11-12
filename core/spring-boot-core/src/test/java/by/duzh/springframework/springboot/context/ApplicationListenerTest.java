package by.duzh.springframework.springboot.context;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationListener;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class})
public class ApplicationListenerTest {

    static class MyApplicationListener implements ApplicationListener<SpringApplicationEvent> {
        @Override
        public void onApplicationEvent(SpringApplicationEvent event) {
            System.out.println("TEST:" + event);
        }
    }

    @Test
    void name() {
        ;
    }
}
