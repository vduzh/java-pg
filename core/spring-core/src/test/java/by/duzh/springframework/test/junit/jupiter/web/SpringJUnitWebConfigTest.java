package by.duzh.springframework.test.junit.jupiter.web;

import by.duzh.aspectj.lang.annotation.PointCutTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(resourcePath = "src/test/webapp")
public class SpringJUnitWebConfigTest {

    @Controller
    @RequestMapping("/bars")
    static class BarController {
        @Autowired
        private BarService service;


        @RequestMapping("/123")
        public String get(String id) {
            return service.findById(1);
        }

    }

    interface BarService {
        String findById(int id);
    }

    @Configuration
    static class TestConfig {

        @Bean
        public BarService barService() {
            return Mockito.mock(BarService.class);
        }
    }

    //@Autowired
    BarService barService;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    MockServletContext servletContext;

    @Autowired
    MockHttpSession session;

    @Autowired
    MockHttpServletRequest request;

    @Autowired
    MockHttpServletResponse response;

    @Autowired
    ServletWebRequest webRequest;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    void test() {
        assertNotNull(ctx);
        //Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        when(barService.findById(1)).thenReturn("One");

        //mockMvc.perform(get())


    }

    @Test
    void name() {
/*
        MockMvc mockMvc = standaloneSetup(new MusicController())
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
*/
    }
}
