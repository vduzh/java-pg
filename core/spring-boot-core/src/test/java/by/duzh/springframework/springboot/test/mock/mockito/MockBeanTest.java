package by.duzh.springframework.springboot.test.mock.mockito;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import by.duzh.springframework.springboot.beans.Bar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class, MockBeanTest.SomeService.class, Bar.class})
public class MockBeanTest {

    static class SomeService {
        @Autowired
        private Bar bar;

        public String say() {
            return "Say:" + bar.value();
        }
    }

    @MockBean
    Bar bar;

    @Test
    void name(@Autowired SomeService service) {
        when(bar.value()).thenReturn("test");
        assertEquals("Say:test", service.say());
    }
}
