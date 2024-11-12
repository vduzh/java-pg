package by.duzh.springframework.springboot.availability;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, AvailabilityChangeEventTest.ReadinessStateExporter.class})
public class AvailabilityChangeEventTest {

    @Component
    static class ReadinessStateExporter {

        @EventListener
        public void onStateChange(AvailabilityChangeEvent<ReadinessState> event) {
            switch (event.getState()) {
                case ACCEPTING_TRAFFIC -> System.out.println("create file /tmp/healthy");
                case REFUSING_TRAFFIC -> System.out.println("remove file /tmp/healthy");
            }
        }
    }

    @Test
    void test() {
        ;
    }
}
