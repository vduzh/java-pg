package by.duzh.springframework.stereotype;

import by.duzh.springframework.stereotype.beans.Hotel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerCRUDTest {
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

    @ControllerAdvice(assignableTypes = HotelController.class)
    public static class CustomDefaultHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
        public CustomDefaultHandlerExceptionResolver() {
            setOrder(Ordered.HIGHEST_PRECEDENCE);
        }

//        @Override
        protected ModelAndView handleTypeMismatch(TypeMismatchException e, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", "Invalid type", e.getMessage()));
        }

//        @Override
        protected ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", ex.getMessage(), ex.getMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ModelAndView handleEntityNotFoundException(EntityNotFoundException e) {
            return new ModelAndView("error", "error", new ErrorResponse("404", e.getMessage(), e.getMessage()));
        }

//        @Override
        protected ModelAndView handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            return new ModelAndView("error", "error", new ErrorResponse("400", "Validation failed", ex.getMessage()));
        }
    }

    MockMvc mockMvc;

    @Controller
    @RequestMapping(value = "/web/hotels")
    public static class HotelController {
        private final Map<Integer, Hotel> HOTELS = new LinkedHashMap<>();

        public HotelController() {
            HOTELS.put(1, new Hotel(1, "Minsk"));
            HOTELS.put(2, new Hotel(2, "Sport"));
        }

        @GetMapping(value = "/list.html")
        public ModelAndView list(@RequestParam(required = false) String name) {
            // filter list if necessary
            var hotels = name == null ? new ArrayList<>(HOTELS.values())
                    : HOTELS.values().stream().filter(hotel -> hotel.getName().equals(name)).collect(Collectors.toList());

            return new ModelAndView("hotel-list", "hotels", hotels);
        }

        @GetMapping("/details.html")
        public ModelAndView getById(@RequestParam int id) {
            var hotelOptional = Optional.ofNullable(HOTELS.get(id));

            if (hotelOptional.isEmpty()) {
                throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(id));
            }

            return new ModelAndView("hotel-details", Collections.singletonMap("hotel", hotelOptional.get()));
        }

        @GetMapping("/add.html")
        public ModelAndView addForm() {
            Hotel hotel = new Hotel("Hotel #" + (HOTELS.size() + 1));

            return new ModelAndView("hotel-form", Collections.singletonMap("hotel", hotel));
        }

        @GetMapping("/edit.html")
        public ModelAndView editForm(@RequestParam int id) {
            var hotelOptional = Optional.ofNullable(HOTELS.get(id));

            if (hotelOptional.isEmpty()) {
                throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(id));
            }

            return new ModelAndView("hotel-form", Collections.singletonMap("hotel", hotelOptional.get()));
        }

        @PostMapping("/save.html")
        public ModelAndView save(@Validated /*@RequestBody*/ Hotel hotel, RedirectAttributes attributes) {
            Hotel result = null;

            // create a new hotel
            if (hotel.getId() == null) {
                hotel.setId(HOTELS.size() + 1);
                HOTELS.put(hotel.getId(), hotel);
                result = hotel;
            } else {
                // update an existing hotel if any
                var hotelOptional = Optional.ofNullable(HOTELS.get(hotel.getId()));

                if (hotelOptional.isEmpty()) {
                    throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(hotel.getId()));
                }

                result = hotelOptional.get();
                result.setName(hotel.getName());
            }


            // send redirect
            attributes.addFlashAttribute("hotel", result);
            return new ModelAndView("redirect:/web/hotels/list.html");
        }

        @GetMapping("/delete.html")
        public ModelAndView delete(@RequestParam int id, RedirectAttributes attributes) {
            // remove hotel if any
            var hotelOptional = Optional.ofNullable(HOTELS.remove(id));

            if (hotelOptional.isEmpty()) {
                throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(id));
            }

            // send redirect
            attributes.addFlashAttribute("hotel", hotelOptional.get());
            return new ModelAndView("redirect:/web/hotels/list.html");
        }
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HotelController())
                .setControllerAdvice(CustomDefaultHandlerExceptionResolver.class)
                /*.defaultRequest(get("/web/hotels/list.html")*//*.contextPath("/web").servletPath("/web")*//*)*/
                .build();
    }

    @Test
    void list() throws Exception {
        MvcResult result = mockMvc.perform(get("/web/hotels/list.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-list"))
                .andExpect(model().attributeExists("hotels"))
                .andReturn();

        List<Hotel> hotels = (List<Hotel>) result.getModelAndView().getModel().get("hotels");
        assertEquals(2, hotels.size());
        assertTrue(hotels.stream().allMatch(h -> (h.id == 1 || h.id == 2) && (h.name.equals("Minsk") || h.name.equals("Sport"))));

        // specify query parameters
        result = mockMvc.perform(get("/web/hotels/list.html").param("name", "Minsk"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-list"))
                .andExpect(model().attributeExists("hotels"))
                .andReturn();
        hotels = (List<Hotel>) result.getModelAndView().getModel().get("hotels");
        assertEquals(1, hotels.size());
        assertTrue(hotels.stream().allMatch(h -> h.id == 1 && h.name.equals("Minsk")));
    }

    @Test
    void getByIdOK() throws Exception {
        mockMvc.perform(get("/web/hotels/details.html?id={value}", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-details"))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("id", Matchers.equalTo(2))))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("name", Matchers.equalTo("Sport"))));
    }

    @Test
    void getByIdMissingId() throws Exception {
        mockMvc.perform(get("/web/hotels/details.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Required int parameter 'id' is not present"))));
    }

    @Test
    void getByIdInvalidIdType() throws Exception {
        mockMvc.perform(get("/web/hotels/details.html?id=test"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Invalid type"))));
    }

    @Test
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get("/web/hotels/details.html?id={value}", 200))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("404"))))
                .andExpect(model().attribute("error", hasProperty("message",
                        equalTo("A hotel with id = %d not found!".formatted(200)))));
    }

    @Test
    void addForm() throws Exception {
        mockMvc.perform(get("/web/hotels/add.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-form"))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("id", Matchers.nullValue())))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("name", Matchers.equalTo("Hotel #3"))));
    }

    @Test
    void editFormOK() throws Exception {
        mockMvc.perform(get("/web/hotels/edit.html").param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-form"))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("id", Matchers.equalTo(2))))
                .andExpect(model().attribute("hotel", Matchers.hasProperty("name", Matchers.equalTo("Sport"))));
    }

    @Test
    void editFormMissingId() throws Exception {
        mockMvc.perform(get("/web/hotels/edit.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Required int parameter 'id' is not present"))));
    }

    @Test
    void editFormInvalidIdType() throws Exception {
        mockMvc.perform(get("/web/hotels/edit.html?id=test"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Invalid type"))));
    }

    @Test
    void editFormNotFound() throws Exception {
        mockMvc.perform(get("/web/hotels/edit.html?id={value}", 200))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("404"))))
                .andExpect(model().attribute("error", hasProperty("message",
                        equalTo("A hotel with id = %d not found!".formatted(200)))));
    }

    @Test
    void save() throws Exception {
        // Add
        mockMvc.perform(post("/web/hotels/save.html").param("name", "Belarus"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("hotel"))
                .andExpect(redirectedUrl("/web/hotels/list.html"));

        // Update
        mockMvc.perform(post("/web/hotels/save.html").param("id", "1").param("name", "Minsk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("hotel"))
                .andExpect(redirectedUrl("/web/hotels/list.html"));
    }

    @Test
    void saveEntityInvalid() throws Exception {
        mockMvc.perform(post("/web/hotels/save.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Validation failed"))));
    }

    @Test
    void saveEntityNotFound() throws Exception {
        mockMvc.perform(post("/web/hotels/save.html").param("id", "200").param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("404"))))
                .andExpect(model().attribute("error", hasProperty("message",
                        equalTo("A hotel with id = %d not found!".formatted(200)))));
    }

    @Test
    void deleteOK() throws Exception {
        mockMvc.perform(get("/web/hotels/delete.html").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("hotel"))
                .andExpect(redirectedUrl("/web/hotels/list.html"));
    }

    @Test
    void deleteMissingId() throws Exception {
        mockMvc.perform(get("/web/hotels/delete.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Required int parameter 'id' is not present"))));
    }

    @Test
    void deleteInvalidIdType() throws Exception {
        mockMvc.perform(get("/web/hotels/delete.html?id=test"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("400"))))
                .andExpect(model().attribute("error", hasProperty("message", equalTo("Invalid type"))));
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(get("/web/hotels/delete.html?id={value}", 200))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("error", hasProperty("code", equalTo("404"))))
                .andExpect(model().attribute("error", hasProperty("message",
                        equalTo("A hotel with id = %d not found!".formatted(200)))));
    }

/*
    @Test
    void testMultipart() throws Exception {
        mockMvc.perform(multipart("/doc").file("a1", "ABC".getBytes(StandardCharsets.UTF_8)));
    }
*/
}
