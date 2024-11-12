package by.duzh.springframework.web;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MockMvcUtil {
    public static MockMvc buildMockMvc(Class<?> klass) {
        try {
            return MockMvcBuilders.standaloneSetup(klass.getDeclaredConstructor().newInstance()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
