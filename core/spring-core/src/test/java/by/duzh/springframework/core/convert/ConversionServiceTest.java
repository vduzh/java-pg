package by.duzh.springframework.core.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionServiceTest {
    private ConversionService conversionService;

    @BeforeEach
    void init() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new Converter<String, Integer>() {
            @Override
            public Integer convert(String source) { return Integer.parseInt(source); }
        });
        conversionService.addConverter(new Converter<String, String>() {
            @Override
            public String convert(String source) { return source.toUpperCase(); }
        });

        this.conversionService = conversionService;
    }

    @Test
    void test() {
       assertEquals(123, conversionService.convert("123", Integer.class).intValue());
       assertEquals("FOO", conversionService.convert("foo", String.class));
    }
}
