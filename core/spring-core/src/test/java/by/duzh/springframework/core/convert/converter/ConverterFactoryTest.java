package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterFactoryTest {
    static class StringToFooConverterFactory implements ConverterFactory<String, Foo> {
        @Override
        public <T extends Foo> Converter<String, T> getConverter(Class<T> targetType) {
            return new StringToFooConverter<T>();
        }

        private static final class StringToFooConverter<T extends Foo> implements Converter<String, T> {

            @Override
            public T convert(String source) {
                return (T) new Foo(Integer.parseInt(source), "name-" + source);
            }
        }
    }

    @Test
    void name() {
        StringToFooConverterFactory factory = new StringToFooConverterFactory();
        var converter = factory.getConverter(Foo.class);
        Foo foo = converter.convert("123");
        assertEquals(123, foo.getId());
        assertEquals("name-" + 123, foo.getName());
    }
}
