package by.duzh.springframework.stereotype;

import by.duzh.springframework.stereotype.beans.Bar;
import by.duzh.springframework.stereotype.beans.Foo;
import by.duzh.springframework.stereotype.beans.User;
import by.duzh.springframework.stereotype.commandobject.FooForm;
import by.duzh.springframework.stereotype.validators.BarValidator;
import by.duzh.springframework.web.MockMvcUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import static org.hamcrest.Matchers.*;

public class ControllerTest {
    MockMvc mockMvc;

    @Controller
    @SessionAttributes({"user"})
    public static class FooController {

        // Initializes the model prior to any @RequestMapping method invocation
        @ModelAttribute
        public void initUser(@RequestParam(required = false) String id, Model model) {
            model.addAttribute("user", new User(1, "Admin"));
        }

        // Simplified version that adds only one attribute
        @ModelAttribute("user2") // of the name is not specified than the default one ("user") is used
        public User initSecondUser() {
            return new User(2, "Admin2");
        }

        @GetMapping("/message")
        public String handleRequest(Model model) {
            model.addAttribute("message", "Foo");
            return "index";
        }

        @GetMapping("/uri-builder/users")
        public String uriComponentsBuilder(UriComponentsBuilder builder) {
            UriComponents uriComponents = builder.build();
            return "index";
        }

        @RequestMapping(path = "/matrix-variables/{countryId}")
        public String matrixVariables(@PathVariable int countryId, @MatrixVariable String color, @MatrixVariable int size) {
            return "index-%d-%s-%d".formatted(countryId, color, size);
        }

        @GetMapping("/request-param")
        public String requestParam(@RequestParam int id, @RequestParam(required = false, defaultValue = "foo") String name,
                                   @RequestParam Optional<Integer> size) {
            return "index-%d-%s-%d-%d".formatted(id, name, size.orElse(null));
        }

        @GetMapping("/request-param-with-map")
        public String requestParamWithMap(@RequestParam Map<String, Integer> params) {
            return params.entrySet().stream().map(e -> "%s=%s".formatted(e.getKey(), e.getValue())).collect(Collectors.joining("-"));
        }

        @GetMapping("/request-param-with-multi-value-map")
        public String requestParamWithMultiValueMap(@RequestParam MultiValueMap<String, Integer> params) {
            return params.entrySet().stream().map(e -> "%s=%s".formatted(e.getKey(), e.getValue())).collect(Collectors.joining("-"));
        }

        @GetMapping("/request-header")
        public String requestHeader(@RequestHeader String foo) {
            return "index-%s".formatted(foo);
        }

        @GetMapping("/cookie-value")
        public String cookieValue(@CookieValue(required = false) String foo) {
            return "index-%s".formatted(foo);
        }

        @PutMapping("/model-attribute/{foo}")
        public String modelAttribute(@ModelAttribute("foo") Foo foo) {
            System.out.println("foo=" + foo);
            return "index-%d-%s".formatted(foo.getId(), foo.getName());
        }

        @GetMapping("/model-attribute-to-init-model")
        public String modelAttributeToInitModel(@ModelAttribute("user2") User user) {
            return "index-%d-%s".formatted(user.id, user.name);
        }

        @GetMapping(value = "/model-attribute-to-return-value-via-model")
        @ModelAttribute("foo")
        public Foo modelAttributeToReturnValueViaModel() {
            return new Foo(1, "Test");
        }

        @PutMapping("/binding-result/{foo}")
        public String bindingResult(@Validated @ModelAttribute("bar") Bar bar, BindingResult bindingResult) {
            System.out.println(bindingResult.hasErrors());
            return "index";
        }

        @PutMapping("/session-attributes/{id}")
        public String sessionAttributes(@ModelAttribute User user, SessionStatus status) {
            return "index-%d-%s".formatted(user.id, user.name);
        }

        @PutMapping("/session-attribute/{id}")
        public String sessionAttribute(@SessionAttribute User guest) {
            return "index-%d-%s".formatted(guest.id, guest.name);
        }

        @GetMapping("/request-attribute/{id}")
        public String requestAttribute(@RequestAttribute("currentUser") User user) {
            return "index-%d-%s".formatted(user.id, user.name);
        }

        @PutMapping("/redirect-core/{id}")
        public ModelAndView redirectCore(ModelMap model) {
            //NOTE: better option than RedirectView
            model.addAttribute("barId", "99"); // will be available as path variable
            model.addAttribute("cityId", "500"); // will be available as path variable
            return new ModelAndView("redirect:/foo/{id}/{barId}?city={cityId}", model); // redirect relative to the current Servlet context.
        }

        @PostMapping("/redirect-view")
        public RedirectView redirectView(RedirectAttributes attributes) {
            //NOTE: RedirectView is suboptimal
            attributes.addFlashAttribute("someName", "John");
            attributes.addAttribute("id", "15"); // will be exposed via path variable
            attributes.addAttribute("city", "600"); // will be exposed as request param

            return new RedirectView("/foo/{id}");
        }

        @PostMapping("/flash-output")
        public RedirectView flashOutput(RedirectAttributes attributes) {
            attributes.addFlashAttribute("name", "John");
            return new RedirectView("/flash-input");
        }

        @GetMapping("/flash-input")
        public String flashInput(Model model) {
            String flashData = (String) model.getAttribute("name");
            return "index-%s".formatted(flashData);
        }

        @PostMapping("/file-upload")
        public String fileUpload(@RequestParam String name, @RequestParam("file") MultipartFile file) throws Exception {
            return "file-upload-%s-%s".formatted(name, new String(file.getBytes()));
        }

        @PostMapping("/file-uploads")
        public String fileUploads(@RequestParam String name, @RequestParam("file") List<MultipartFile> file) throws Exception {
            var res = file.stream()
                    .map(f -> {
                        try {
                            return f.getBytes();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(String::new)
                    .collect(Collectors.joining("-"));

            return "file-upload-%s-%s".formatted(name, res);
        }

        @PostMapping("/file-upload-command-object")
        public String fileUploadCommandObject(FooForm form, BindingResult errors) throws Exception {
            return "file-upload-%s-%s".formatted(form.getName(), new String(form.getFile().getBytes()));
        }

        @PostMapping("/request-body")
        public String requestBody(@RequestBody Foo foo) {
            return "request-body-%s-%s".formatted(foo.getId(), foo.getName());
        }

        @PostMapping("/http-entity")
        public String httpEntity(HttpEntity<Foo> entity) {
            System.out.println("header: = " + entity.getHeaders().get("test"));
            System.out.println("body: = " + entity.getBody());
            //return "request-body-%s-%s".formatted(foo.getId(), foo.getName());
            return "http-entity-1";
        }

        @GetMapping("/response-body")
        @ResponseBody
        public Foo responseBody() {
            return new Foo(1, "test");
        }

        @GetMapping("/response-entity")
        public ResponseEntity<Foo> responseEntity() {
            return ResponseEntity.ok().eTag("someTag").body(new Foo(2, "test2"));
        }

        @GetMapping("/json-view-resolution-without-id")
        public String jsonViewResolutionWithoutId(Model model) {
            model.addAttribute("foo", new Foo(1, "test1"));
            model.addAttribute(JsonView.class.getName(), Foo.WithoutId.class);
            return "fooView";
        }
    }

    @Test
    void handleRequest() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/message"))
                .andExpect(status().isOk()).andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Foo"));
    }

    @Test
    void uriComponentsBuilder() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/uri-builder/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void matrixVariables() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/matrix-variables/12;color=wrw;size=123"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(view().name("index-12-wrw-123"));

    }

    @Test
    void requestParam() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param?id=123&name=Test&size=10"))
                .andExpect(status().isOk()).andExpect(view().name("index-123-Test-10"));

        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param?id=123&name=Test"))
                .andExpect(status().isOk()).andExpect(view().name("index-123-Test-null"));

        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param?id=123"))
                .andExpect(status().isOk()).andExpect(view().name("index-123-foo-null"));

        // id is not specified
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param")).andExpect(status().isBadRequest());

    }

    @Test
    void requestParamWithMap() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param-with-map?id=123&name=Test"))
                .andExpect(status().isOk()).andExpect(view().name("id=123-name=Test"));
    }

    @Test
    void requestParamWithMultiValueMap() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-param-with-multi-value-map?id=123&name=Test&name=Test2"))
                .andExpect(status().isOk()).andExpect(view().name("id=[123]-name=[Test, Test2]"));
    }

    @Test
    void cookieValue() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/cookie-value").cookie(new Cookie("foo", "bar")))
                .andExpect(status().isOk()).andExpect(view().name("index-bar"));
    }

    @Test
    void requestHeader() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/request-header").header("foo", "bar"))
                .andExpect(status().isOk()).andExpect(view().name("index-bar"));
    }

    @Test
    void modelAttribute() throws Exception {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new Converter<String, Foo>() {
            @Override
            public Foo convert(String text) {
                return new Foo(Integer.parseInt(text), "name-" + text);
            }
        });

        // Register custom conversion service
        MockMvcBuilders.standaloneSetup(new FooController()).setConversionService(conversionService).build()
                .perform(put("/model-attribute/123"))
                .andExpect(status().isOk()).andExpect(view().name("index-123-name-123"));
    }

    @Test
    void modelAttributeToInitModel() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(get("/model-attribute-to-init-model"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user2"))
                .andExpect(view().name("index-2-Admin2"));
    }

    @Test
    void modelAttributeToReturnValueViaModel() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(get("/model-attribute-to-return-value-via-model"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("foo"));
    }

    @Test
    void bindingResult() throws Exception {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new Converter<String, Bar>() {
            @Override
            public Bar convert(String text) {
                return new Bar(Integer.parseInt(text), "b");
            }
        });

        // Register custom conversion service
        MockMvcBuilders.standaloneSetup(new FooController())
                .setConversionService(conversionService).setValidator(new BarValidator()).build()
                .perform(put("/binding-result/123"))
                .andExpect(status().isOk()).andExpect(view().name("index-123-name-123"));
    }

    @Test
    void sessionAttributes() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(put("/session-attributes/555"))
                .andExpect(status().isOk()).andExpect(view().name("index-1-Admin"));
    }

    @Test
    void sessionAttribute() throws Exception {
        // Setup session attribute in the interceptor
        HandlerInterceptor interceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                request.getSession().setAttribute("guest", new User(100, "Guest"));
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            }
        };

        MockMvcBuilders.standaloneSetup(new FooController())
                .addInterceptors(interceptor)
                .build()
                .perform(put("/session-attribute/555"))
                .andExpect(status().isOk()).andExpect(view().name("index-100-Guest"));
    }

    @Test
    void requestAttribute() throws Exception {
        // Setup request attribute in the interceptor
        HandlerInterceptor interceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                request.setAttribute("currentUser", new User(99, "Developer"));
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            }
        };

        MockMvcBuilders.standaloneSetup(new FooController())
                .addInterceptors(interceptor)
                .build()
                .perform(get("/request-attribute/666"))
                .andExpect(status().isOk()).andExpect(view().name("index-99-Developer"));
    }

    @Test
    void redirectCore() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(put("/redirect-core/123"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/foo/123/99?city=500"));
    }

    @Test
    void redirectView() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(post("/redirect-view"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/foo/15?city=600"));
    }

    @Test
    void flashOutput() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(post("/flash-output"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("name"))
                .andExpect(redirectedUrl("/flash-input"));
    }

    @Test
    void flashInput() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(get("/flash-input").flashAttr("name", "John"))
                .andExpect(status().isOk())
                .andExpect(view().name("index-John"));
    }

    @Test
    void fileUpload() throws Exception {
        MockMvcBuilders.standaloneSetup(new FooController()).build()
                .perform(multipart("/file-upload").file("file", "Hello".getBytes()).param("name", "test"))
                .andExpect(status().isOk()).andExpect(view().name("file-upload-test-Hello"));
    }

    @Test
    void fileUploads() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(multipart("/file-uploads")
                        .file("file", "Hello".getBytes())
                        .file("file", "World".getBytes())
                        .param("name", "test"))
                .andExpect(status().isOk()).andExpect(view().name("file-upload-test-Hello-World"));
    }

    @Test
    void fileUploadCommandObject() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(multipart("/file-upload-command-object")
                        .file("file", "Hello".getBytes())
                        .param("name", "test"))
                .andExpect(status().isOk()).andExpect(view().name("file-upload-test-Hello"));
    }

    @Test
    void requestBody() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(post("/request-body")
                        .param("id", "100")
                        .param("name", "test"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(view().name("request-body-100-test"));
    }

    @Test
    void httpEntity() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class)
                .perform(post("/http-entity").characterEncoding("UTF-8").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "100")
                        .param("name", "test")
                        .content("id=1&name=buz")
                )
                .andDo(print())
                .andExpect(status().isOk()).andExpect(view().name("request-body-100-test"));
    }

    @Test
    void responseBody() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/response-body"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));
        //.andExpect(MockMvcResultMatchers.content().json("123"));
    }

    @Test
    void responseEntity() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/response-entity"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test2"));
    }

    @Test
    void jsonViewResolutionWithoutId() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/json-view-resolution-without-id"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(view().name("fooView"));
    }
}
