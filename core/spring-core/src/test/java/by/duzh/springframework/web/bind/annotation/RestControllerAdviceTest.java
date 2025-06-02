package by.duzh.springframework.web.bind.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

// TODO: have a look at https://www.baeldung.com/exception-handling-for-rest-with-spring
public class RestControllerAdviceTest {
    private static final Logger logger = Logger.getLogger(RestControllerAdviceTest.class.getName());

    // Target all Controllers annotated with @RestController
    //@ControllerAdvice(annotations = RestController.class)
    @ControllerAdvice(annotations = RestController.class)
    public static class ExampleAdvice1 {}

    @RestController
    static class FooRest {

        @RequestMapping("/user/{id}")
        public String getByd(@PathVariable int id) {
            return "";
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
