package by.duzh.springframework.web.bind.annotation;

import by.duzh.springframework.web.MockMvcUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RequestBodyTest {
    @Controller
    static class FooController {
        static class Foo {
            public String name;

            public Foo() {
            }

            public Foo(String name) {
                this.name = name;
            }
        }

        @PostMapping("/post-foo")
        public String add(@RequestBody Foo foo, Model model) {
            //model.addAttribute("foo", foo);
            return "index";
        }
    }

    @Test
    void methodLevelMappings() throws Exception {

        MockMvcUtil.buildMockMvc(FooController.class).perform(post("/post-foo")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        //.contentType("application/x-www-form-urlencoded")
                        /*.param("name", "Test")*/
                        .content("name=Test")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
        //.andExpect(model().attribute("foo", Matchers.hasProperty("name", Matchers.equalTo("Test"))))
        ;
    }
}
