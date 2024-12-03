package by.duzh.springframework.web.bind.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//NOTE: Look at https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/

public class ExceptionHandlerTest {

    public static class Foo {
        private Integer id;

        @NotEmpty
        private String name;

        @NotEmpty
        @Email
        private String email;

        public Foo() {
        }

        public Foo(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public Foo(Integer id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Person{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String exception) {
            super(exception);
        }
    }

    public static class ErrorResponse {
        public String code;
        public String message;
        public List<String> details;

        public ErrorResponse(String code, String message, String... details) {
            this.code = code;
            this.message = message;
            this.details = new LinkedList<>();
            if (details != null) {
                this.details.addAll(Arrays.asList(details));
            }
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getDetails() {
            return details;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }

        public void addDetail(String detail) {
            this.details.add(detail);
        }
    }

    @ControllerAdvice(assignableTypes = FooController.class)
    public static class CustomDefaultHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
        public CustomDefaultHandlerExceptionResolver() {
            setOrder(Ordered.HIGHEST_PRECEDENCE);
        }

        @Override
        protected ModelAndView handleTypeMismatch(TypeMismatchException e, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", "Invalid type", e.getMessage()));
        }

/*
        @Override
        protected ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", "Validation failed", e.getMessage()));
        }
*/

        @Override
        protected ModelAndView handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", "Validation failed", ex.getMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ModelAndView handleEntityNotFoundException(EntityNotFoundException e) {
            return new ModelAndView("error", "error", new ErrorResponse("404", e.getMessage(), e.getMessage()));
        }
    }

    @Controller
    public static class FooController {

        @GetMapping("/foo.html")
        public String webGet(@RequestParam int id) throws Exception {
            throw new EntityNotFoundException("Foo with id = %d not found!".formatted(id));
        }

        @PostMapping("/save.html")
        public Foo webPost(@Valid /*@RequestBody*/ Foo foo/*, BindingResult binding*/) { //TODO: use Request body here!!!
            /*System.out.println(binding.hasErrors());*/
            foo.id = 1;
            return foo;
        }
    }

    @Test
    void webGet() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(new FooController())
                .setControllerAdvice(new CustomDefaultHandlerExceptionResolver())
                //.setHandlerExceptionResolvers(new CustomDefaultHandlerExceptionResolver())
                .build();

        mockMvc.perform(get("/foo.html").param("id", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Invalid type"))));

        mockMvc.perform(get("/foo.html").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("404"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Foo with id = %d not found!".formatted(1)))));
    }

    @Test
    void webPost() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(new FooController())
                .setControllerAdvice(new CustomDefaultHandlerExceptionResolver())
                .build();

        mockMvc.perform(post("/save.html").param("name", "").param("email", "tst@test.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Validation failed"))));
    }

    @RestControllerAdvice(assignableTypes = FooRestController.class)
    static class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

        //@Override
        protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", "Invalid type", e.getMessage()));
        }

//        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
            List<String> details = new ArrayList<>();
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                details.add(error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400",
                    "Validation failed", (String[]) details.toArray((new String[0]))));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("404", e.getMessage(), e.getMessage()));
        }
    }

    @RestController
    public static class FooRestController {

        @GetMapping("/foo/{id}")
        public Foo getFoo(@PathVariable int id) {
            throw new EntityNotFoundException("Foo with id = %d not found!".formatted(id));
        }

        @PostMapping("/foo")
        public Foo postFoo(@Validated @RequestBody Foo foo/*, BindingResult binding*/) {
            /*System.out.println(binding.hasErrors());*/
            foo.id = 1;
            return foo;
        }
    }

    @Test
    void restGetInvalidId() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(new FooRestController())
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();

        mockMvc.perform(get("/foo/test"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Invalid type"));
    }

    @Test
    void restGetNotFound() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(new FooRestController())
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();

        mockMvc.perform(get("/foo/123"))
                //.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("Foo with id = %d not found!".formatted(123)));
    }

    @Test
    void restPostInvalidArgument() throws Exception {
        var mockMvc = MockMvcBuilders
                .standaloneSetup(new FooRestController())
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();

        mockMvc.perform(post("/foo")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Foo("", "invalidemail"))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
}
