package by.duzh.springframework.web.bind.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RequestMappingTest {
    private MockMvc buildMockMvc(Class<?> klass) {
        try {
            return MockMvcBuilders.standaloneSetup(klass.getDeclaredConstructor().newInstance()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class Foo {
        public String name;

        public Foo() {
        }

        public Foo(String name) {
            this.name = name;
        }
    }

    @Controller
    static class FooController {
        @RequestMapping("/method-mapping")
        public String methodMapping() {
            return "index";
        }

        @RequestMapping(value = "/request-mapping-get", method = RequestMethod.GET)
        public String mappingByHTTMethod() {
            return "index";
        }

        @GetMapping("/do-get-shortcut")
        public String getMappingShortcut() {
            return "index";
        }

        @PostMapping("/do-post-shortcut")
        public String postMappingShortcut() {
            return "index";
        }

        @PutMapping("/do-put-shortcut")
        public String putMappingShortcut() {
            return "index";
        }

        @DeleteMapping("/do-delete-shortcut")
        public String deleteMappingShortcut() {
            return "index";
        }

        @PatchMapping("/do-patch-shortcut")
        public String patchMappingShortcut() {
            return "index";
        }

        @RequestMapping("/path-variable/{id}")
        public String pathVariable(@PathVariable int id) {
            return "index-" + id;
        }

        @RequestMapping("/uri-pattern/{countryId}/cities/{cityId}")
        public String uriPatterns(@PathVariable int countryId, @PathVariable int cityId) {
            return "index-" + countryId + "-" + cityId;
        }

        @RequestMapping(path = "/use-consumes-to-match", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
        public String narrowMappingByContentType(@RequestBody Foo foo) {
            return "index-" + foo.name;
        }

        @RequestMapping(path = "/use-produce-to-match", produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public Foo narrowMappingByAcceptRequestHeader() {
            return new Foo("test");
        }

        @RequestMapping(path = "/match-by-param", params = {"country=Belarus"})
        public String narrowMappingByParamValue() {
            return "index";
        }

        @RequestMapping(path = "/match-by-header", headers = {"language=Belarusian"})
        public String narrowMappingByHeaderValue() {
            return "index";
        }
    }

    @Test
    void methodLevelMappings() throws Exception {
        buildMockMvc(FooController.class).perform(get("/method-mapping"))
                .andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void mappingByHTTMethod() throws Exception {
        buildMockMvc(FooController.class).perform(get("/request-mapping-get"))
                .andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void httpMethodSpecificShortcuts() throws Exception {
        var mockMvc = buildMockMvc(FooController.class);

        mockMvc.perform(get("/do-get-shortcut")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(post("/do-post-shortcut")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(put("/do-put-shortcut")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(delete("/do-delete-shortcut")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(patch("/do-patch-shortcut")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void pathVariable() throws Exception {
        buildMockMvc(FooController.class).perform(get("/path-variable/123"))
                .andExpect(status().isOk()).andExpect(view().name("index-123"));
    }

    @Test
    void uriPatterns() throws Exception {
        buildMockMvc(FooController.class).perform(get("/uri-pattern/10/cities/15"))
                .andExpect(status().isOk()).andExpect(view().name("index-10-15"));
    }

    @Test
    void narrowMappingByContentType() throws Exception {
        buildMockMvc(FooController.class).perform(
                post("/use-consumes-to-match")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(new Foo("test")))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk()).andExpect(view().name("index-test"));
    }

    @Test
    void narrowMappingByAcceptRequestHeader() throws Exception {
        buildMockMvc(FooController.class).perform(post("/use-produce-to-match").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // Error: 406 Not Acceptable
        buildMockMvc(FooController.class).perform(post("/use-produce-to-match").accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void narrowMappingByParamValue() throws Exception {
        buildMockMvc(FooController.class).perform(get("/match-by-param").param("country", "Belarus"))
                .andExpect(status().isOk()).andExpect(view().name("index"));

        buildMockMvc(FooController.class).perform(get("/match-by-param").param("country", "Poland"))
                .andExpect(status().isBadRequest());
        buildMockMvc(FooController.class).perform(get("/match-by-param"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void narrowMappingByHeaderValue() throws Exception {
        buildMockMvc(FooController.class).perform(get("/match-by-header").header("language", "Belarusian"))
                .andExpect(status().isOk()).andExpect(view().name("index"));

        buildMockMvc(FooController.class).perform(get("/match-by-header").header("language", "English"))
                .andExpect(status().isNotFound());
        buildMockMvc(FooController.class).perform(get("/match-by-language"))
                .andExpect(status().isNotFound());

    }

    @Controller
    @RequestMapping("/bars")
    static class BarController {

        @GetMapping("/bar")
        public String getBar() {
            return "index";
        }
    }

    @Test
    void typeAndMethodLevelMappings() throws Exception {
        buildMockMvc(BarController.class).perform(get("/bars/bar"))
                .andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Controller
    @RequestMapping("/buz/{buzId}")
    static class BuzController {

        @GetMapping("/test/{testId}/{version:[a-z-]+}")
        public String getBar(@PathVariable int buzId, @PathVariable("testId") int id, @PathVariable String version) {
            return "index-" + buzId + "-" + id + "-" + version;
        }
    }

    @Test
    void uriPatternOnTypeAndMethod() throws Exception {
        buildMockMvc(BuzController.class).perform(get("/buz/8/test/9/abc"))
                .andExpect(status().isOk()).andExpect(view().name("index-8-9-abc"));
    }
}
