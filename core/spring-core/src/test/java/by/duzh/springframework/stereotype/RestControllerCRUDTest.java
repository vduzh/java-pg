package by.duzh.springframework.stereotype;

import by.duzh.springframework.stereotype.beans.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RestControllerCRUDTest {

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

    @RestControllerAdvice(assignableTypes = HotelRestController.class)
    static class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("400", "Body is missing", ex.getMessage()));
        }

//        @Override
        protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", "Type mismatch", e.getMessage()));
        }

//        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
            List<String> details = new ArrayList<>();
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                details.add(error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    /*.contentType(MediaType.APPLICATION_JSON)*/
                    .body(new ErrorResponse("400",
                            "Validation failed", (String[]) details.toArray((new String[0]))));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    /*.contentType(MediaType.APPLICATION_JSON)*/
                    .body(new ErrorResponse("404", e.getMessage(), e.getMessage()));
        }
    }

    @RestController
    @RequestMapping(value = "/hotels")
    static class HotelRestController {

        private final Map<Integer, Hotel> HOTELS = new LinkedHashMap<>();

        public HotelRestController() {
            HOTELS.put(1, new Hotel(1, "Minsk"));
            HOTELS.put(2, new Hotel(2, "Sport"));
        }

        @RequestMapping(method = RequestMethod.GET)
        public List<Hotel> getList(@RequestParam(required = false) String name) {
            return name == null
                    ? new ArrayList<>(HOTELS.values())
                    : HOTELS.values().stream().filter(hotel -> hotel.getName().equals(name)).collect(Collectors.toList());
        }

        @GetMapping(value = "/{id}")
        public Hotel getById(@PathVariable("id") int id) {
            var hotelOptional = Optional.ofNullable(HOTELS.get(id));
            return hotelOptional.orElseThrow(() -> new EntityNotFoundException("A hotel with id = %d not found!".formatted(id)));
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Hotel post(@NotNull @Validated @RequestBody Hotel hotel) {
            hotel.setId(HOTELS.size() + 1);
            HOTELS.put(hotel.getId(), hotel);
            return hotel;
        }

        @PutMapping("/{id}")
        public Hotel put(@PathVariable("id") int id, @NotNull @Validated @RequestBody Hotel hotel) {
            var hotelOptional = Optional.ofNullable(HOTELS.get(hotel.getId()));

            if (hotelOptional.isEmpty()) {
                throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(hotel.getId()));
            }

            Hotel result = hotelOptional.get();
            result.setName(hotel.getName());

            return result;
        }

        @DeleteMapping(value = "/{id}")
        public void delete(@PathVariable("id") int id) {
            var hotelOptional = Optional.ofNullable(HOTELS.remove(id));

            if (hotelOptional.isEmpty()) {
                throw new EntityNotFoundException("A hotel with id = %d not found!".formatted(id));
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
        mockMvc = MockMvcBuilders.standaloneSetup(new HotelRestController())
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .defaultRequest(
                        get("/hotels/")/*.contextPath("/app")*/
                                .servletPath("/api").accept(MediaType.APPLICATION_JSON)
                )
                .build();
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("2"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Minsk"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Sport"));

        // specify query parameters
        mockMvc.perform(get("/api/hotels").param("name", "Minsk"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("1"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Minsk"));
    }

    @Test
    void testGetOK() throws Exception {
        mockMvc.perform(get("/api/hotels/{id}", 2))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("Sport"));
    }

    @Test
    void getByIdInvalidIdType() throws Exception {
        mockMvc.perform(get("/api/hotels/{id}", "test"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Type mismatch"));
    }

    @Test
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/hotels/{id}", "200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("A hotel with id = %d not found!".formatted(200)));
    }

    @Test
    void testPost() throws Exception {
        Hotel hotel = new Hotel("Belarus");

        mockMvc.perform(post("/api/hotels")
                        .content(new ObjectMapper().writeValueAsString(hotel))
                        .contentType(MediaType.APPLICATION_JSON)
                /*.characterEncoding("UTF-8")*/)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value(hotel.getName()));
    }

    @Test
    void testPostEntityInvalid() throws Exception {
        // request body is missing
        mockMvc.perform(post("/api/hotels").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                /*.andExpect(content().contentType(MediaType.APPLICATION_JSON))*/
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Body is missing"));

        // request body is invalid
        Hotel hotel = new Hotel("");
        mockMvc.perform(post("/api/hotels").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void testPut() throws Exception {
        Hotel hotel = new Hotel(1, "Minsk");

        mockMvc.perform(put("/api/hotels/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(hotel.getName()));
    }

    @Test
    void testPutIdInvalidIdType() throws Exception {
        Hotel hotel = new Hotel(1, "Minsk");

        mockMvc.perform(put("/api/hotels/{id}", "test").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotel)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Type mismatch"));
    }

    @Test
    void testPutEntityInvalid() throws Exception {
        // request body is missing
        mockMvc.perform(put("/api/hotels/{id}", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Body is missing"));

        // request body is invalid
        Hotel hotel = new Hotel("");
        mockMvc.perform(put("/api/hotels/{id}", "1").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/hotels/{id}", 2)).andExpect(status().isOk());
    }

    @Test
    void testDeleteInvalidIdType() throws Exception {
        mockMvc.perform(delete("/api/hotels/{id}", "test"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Type mismatch"));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/hotels/{id}", "200"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("A hotel with id = %d not found!".formatted(200)));
    }



/*
    @Test
    void testMultipart() throws Exception {
        mockMvc.perform(multipart("/doc").file("a1", "ABC".getBytes(StandardCharsets.UTF_8)));
    }
*/
}
