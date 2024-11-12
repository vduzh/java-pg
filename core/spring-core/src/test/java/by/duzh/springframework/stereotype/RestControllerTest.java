package by.duzh.springframework.stereotype;

import by.duzh.springframework.stereotype.beans.Foo;
import by.duzh.springframework.stereotype.beans.Hotel;
import by.duzh.springframework.web.MockMvcUtil;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RestControllerTest {

    @RestController
    public static class FooController {

        @GetMapping("/json-view-without-id")
        @JsonView(Foo.WithoutId.class)
        public Foo jsonViewWithoutId() {
            return new Foo(1, "test1");
        }

        @GetMapping("/json-view-with-id")
        @JsonView(Foo.WithId.class)
        public Foo jsonViewWithId() {
            return new Foo(2, "test2");
        }

        @GetMapping("/mapping-jackson-value-without-id")
        public MappingJacksonValue mappingJsonViewWithoutId() {
            MappingJacksonValue value= new MappingJacksonValue(new Foo(1, "test1"));
            value.setSerializationView(Foo.WithoutId.class);
            return value;
        }

        @GetMapping("/mapping-jackson-value-with-id")
        public MappingJacksonValue mappingJsonViewWithId() {
            MappingJacksonValue value= new MappingJacksonValue(new Foo(2, "test2"));
            value.setSerializationView(Foo.WithId.class);
            return value;
        }
    }

    @Test
    void  jsonViewWithoutId() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/json-view-without-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").value("test1"));
    }

    @Test
    void  jsonViewWithId() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/json-view-with-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

    @Test
    void  mappingJsonViewWithoutId() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/mapping-jackson-value-without-id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").value("test1"));
    }

    @Test
    void  mappingJsonViewWithId() throws Exception {
        MockMvcUtil.buildMockMvc(FooController.class).perform(get("/mapping-jackson-value-with-id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

    @RestController
    @RequestMapping(value = "/hotels")
    static class HotelRestController {

        protected final Log logger = LogFactory.getLog(getClass());

        @RequestMapping(method = RequestMethod.GET)
        public List<Hotel> getList(@RequestParam(required = false) String name) {
            var result = Arrays.asList(new Hotel(1, "Minsk"), new Hotel(2, "Sport"));

            if (name != null) {
                result = result.stream().filter(hotel -> hotel.getName().equals(name)).collect(Collectors.toList());
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Returning hotels: [%s]".formatted(result));
            }

            return result;
        }

        @GetMapping(value = "/{id}")
        public Hotel getById(@PathVariable("id") int id) {
            Hotel hotel = new Hotel(id, "Hotel" + id);
            if (logger.isDebugEnabled()) {
                logger.debug("Returning hotel: [%s]".formatted(hotel));
            }
            return hotel;
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Hotel post(@RequestBody Hotel hotel) {
            if (logger.isDebugEnabled()) {
                logger.debug("Saving hotel: [%s]".formatted(hotel));
            }
            // Generate id
            hotel.setId(2);
            return hotel;
        }

        @PutMapping("/{id}")
        public Hotel put(@PathVariable("id") int id, @RequestBody Hotel hotel) {
            if (logger.isDebugEnabled()) {
                logger.debug("Updating hotel: [%s]".formatted(hotel));
            }
            return hotel;
        }

        @DeleteMapping(value = "/{id}")
        public void delete(@PathVariable("id") int id) {
            if (logger.isDebugEnabled()) {
                logger.debug("Deleting hotel with id: [%d]".formatted(id));
            }
        }

/*
        @PostMapping("/")
        public String handle(@RequestPart("meta-data") MetaData metadata,
                             @RequestPart("file-data") MultipartFile file) {
            // look at the code here: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestattrib
        }
*/
    }

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
          MockMvcBuilders.standaloneSetup(new HotelRestController())
                .defaultRequest(get("/hotels/")/*.contextPath("/app")*/.servletPath("/api")
                        .accept(MediaType.APPLICATION_JSON))
                //.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.alwaysExpect(content().encoding("UTF-8"))
                .build();
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/api/hotels"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("2"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Minsk"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Sport"));

            // specify query parameters
            mockMvc.perform(get("/api/hotels?name={city}", "Minsk"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("1"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Minsk"));

            // specify query parameters - option #2
            mockMvc.perform(get("/api/hotels").param("name", "Minsk"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("1"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Minsk"));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/api/hotels/{id}", 42))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("42"))
                .andExpect(jsonPath("$.name").value("Hotel42"));
    }

    @Test
    void testPost() throws Exception {
        Hotel hotel = new Hotel("Foo");

        mockMvc.perform(post("/api/hotels")
                .content(new ObjectMapper().writeValueAsString(hotel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value(hotel.getName()));
    }

    @Test
    void testPut() throws Exception {
        Hotel hotel = new Hotel(42, "Bar");

        mockMvc.perform(put("/api/hotels/{id}", 42)
                .content(new ObjectMapper().writeValueAsString(hotel))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("42"))
                .andExpect(jsonPath("$.name").value(hotel.getName()));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/hotels/{id}", 42))
                //.andDo(print())
                .andExpect(status().isOk());
    }

/*
    @Test
    void testMultipart() throws Exception {
        mockMvc.perform(multipart("/doc").file("a1", "ABC".getBytes(StandardCharsets.UTF_8)));
    }
*/
}
