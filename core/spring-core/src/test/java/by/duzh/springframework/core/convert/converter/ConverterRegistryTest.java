package by.duzh.springframework.core.convert.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterRegistryTest {
    private static final Logger logger = Logger.getLogger(ConverterRegistryTest.class.getName());
    private ConversionService conversionService;

    @BeforeEach
    void init() {
        final GenericConversionService conversionService = new GenericConversionService();

        ConverterRegistry converterRegistry = conversionService;
        converterRegistry.addConverter(new Converter<String, Integer>() {
            @Override
            public Integer convert(String source) {
                return Integer.parseInt(source);
            }
        });
        converterRegistry.addConverter(new Converter<String, String>() {
            @Override
            public String convert(String source) {
                return source.toUpperCase();
            }
        });

        this.conversionService = conversionService;
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
