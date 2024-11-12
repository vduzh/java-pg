package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {

    static class FooConverter implements Converter<String, Foo> {

        @Override
        public Foo convert(String source) {
            return new Foo(Integer.parseInt(source), "name-" + source);
        }
    }

    @Test
    void test() {
        Converter<String, Foo> converter = new FooConverter();
        Foo foo = converter.convert("123");

        assertEquals(123, foo.getId());
        assertEquals("name-123", foo.getName());
    }

    @Test
    void withConverterService() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new FooConverter());

        Foo foo = conversionService.convert("123", Foo.class);

        assertEquals(123, foo.getId());
        assertEquals("name-123", foo.getName());
    }
}
