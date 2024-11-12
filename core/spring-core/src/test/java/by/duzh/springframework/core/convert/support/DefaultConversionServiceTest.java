package by.duzh.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultConversionServiceTest {
    @Test
    void test() {
        DefaultConversionService conversionService = new DefaultConversionService();
        assertTrue(conversionService.canConvert(Number.class, String.class));
        //System.out.println(conversionService);
    }
}
