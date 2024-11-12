package by.duzh.springframework.web.bind.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class ControllerAdviceTest {
    // Target all Controllers within specific packages
    @ControllerAdvice("org.example.controllers")
    public static class ExampleAdvice2 {
    }

    // Target all Controllers assignable to specific classes
    //@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
    public static class ExampleAdvice3 {
    }

    @Test
    void name() {
        throw new RuntimeException();
    }
}
