package by.duzh.springframework.springboot.availability;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, ApplicationAvailabilityBean.class})
public class ApplicationAvailabilityTest {

    @Test
    void test(@Autowired ApplicationAvailability availability) {
        assertEquals(LivenessState.CORRECT, availability.getLivenessState());
        assertEquals(ReadinessState.ACCEPTING_TRAFFIC, availability.getReadinessState());
    }
}
