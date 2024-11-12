package by.duzh.springframework.test.servlet.setup;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MockMvcBuildersTest {
    @Controller
    static class FooController {
    }

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new FooController()).build();
    }

}
