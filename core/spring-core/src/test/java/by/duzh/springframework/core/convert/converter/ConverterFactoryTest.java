package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterFactoryTest {
    private static final Logger logger = Logger.getLogger(ConverterFactoryTest.class.getName());

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
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
