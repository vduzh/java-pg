package by.duzh.springframework.test.servlet;

import by.duzh.springframework.beans.factory.FactoryBeanTest;
import by.duzh.springframework.stereotype.beans.Hotel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockMvcTest {

    interface HotelService {
        Hotel get(int id);
    }

    @Controller
    @RequestMapping(value = "/hotels")
    static class HotelController {

        protected final Log logger = LogFactory.getLog(getClass());

        @Autowired
        private HotelService hotelService;

        @GetMapping("/hotel-details.html")
        public ModelAndView getById(@RequestParam int id) {
            if (logger.isTraceEnabled()) {
                logger.trace("Getting list of hotels");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Getting list of hotels");
            }
            return new ModelAndView("hotel-details", Collections.singletonMap("entity", hotelService.get(id)));
        }
    }

    static class MockMvcStandaloneTest {
        HotelService hotelService;

        MockMvc mockMvc;

        @BeforeEach
        void setup() {
            hotelService = Mockito.mock(HotelService.class);

            var controller = new HotelController();
            ReflectionTestUtils.setField(controller, "hotelService", hotelService);

            mockMvc = MockMvcBuilders.standaloneSetup(controller)
                    //.setViewResolvers(getResolver())
                    //.defaultRequest(get("/hotels/").accept(MediaType.APPLICATION_JSON))
                    //.defaultRequest(get("/").contextPath("/app").servletPath("/main")
                    //.alwaysExpect(status().isOk())
                    //.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON))
                    //.alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                    // 2.apply(sharedHttpSession())
                    .build();

        }

        @Test
        void testGet() throws Exception {
            final int id = 1;

            when(hotelService.get(id)).thenReturn(new Hotel(id, "Belarus"));

            //.getDispatcherServlet().
            mockMvc.perform(get("/hotels/hotel-details.html").param("id", String.valueOf(id)))
                    .andDo(print());
        }
    }

    @SpringJUnitWebConfig(value = {FactoryBeanTest.TestConfig.class, HotelController.class})
    static class MockMvcWithWebAppContextTest {

        @Configuration
        static class TestConfig {
            @Bean
            public HotelService hotelService() {
                return Mockito.mock(HotelService.class);
            }
        }

        MockMvc mockMvc;

        @Autowired
        HotelService hotelService;

        @BeforeEach
        void setup(WebApplicationContext wac) {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        void test() throws Exception {
            System.out.println(mockMvc);
            System.out.println(hotelService);
        }
    }
}
