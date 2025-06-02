package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    private static final Logger logger = Logger.getLogger(ConverterTest.class.getName());

    static class FooConverter implements Converter<String, Foo> {

        @Override
        public Foo convert(String source) {
            return new Foo(Integer.parseInt(source), "name-" + source);
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
