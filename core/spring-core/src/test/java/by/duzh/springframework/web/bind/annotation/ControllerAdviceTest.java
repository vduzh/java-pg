package by.duzh.springframework.web.bind.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.logging.Logger;

public class ControllerAdviceTest {
    private static final Logger logger = Logger.getLogger(ControllerAdviceTest.class.getName());

    // Target all Controllers within specific packages
    @ControllerAdvice("org.example.controllers")
    public static class ExampleAdvice2 {
    }

    // Target all Controllers assignable to specific classes
    //@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
    public static class ExampleAdvice3 {
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
