package by.duzh.springframework.web.bind.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.logging.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ResponseStatusTest {
    private static final Logger logger = Logger.getLogger(ResponseStatusTest.class.getName());

    private MockMvc buildMockMvc(Class<?> klass) {
        try {
            return MockMvcBuilders.standaloneSetup(klass.getDeclaredConstructor().newInstance()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Controller
    static class FooController {

        @PostMapping("/add-foo")
        @ResponseStatus(HttpStatus.CREATED)
        public String add() {
            return "index";
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
