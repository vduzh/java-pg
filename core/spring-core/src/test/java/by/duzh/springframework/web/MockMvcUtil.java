package by.duzh.springframework.web;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.logging.Logger;

public class MockMvcUtil {
    private static final Logger logger = Logger.getLogger(MockMvcUtil.class.getName());

    public static MockMvc buildMockMvc(Class<?> klass) {
        try {
            return MockMvcBuilders.standaloneSetup(klass.getDeclaredConstructor().newInstance()).build();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public static void handleException(Exception e) {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
