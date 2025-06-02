package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.logging.Logger;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericConverterTest {
    private static final Logger logger = Logger.getLogger(GenericConverterTest.class.getName());

    /**
     * @see ArrayToCollectionConverter
     * @see ObjectToObjectConverter
     */
    // look into ArrayToCollectionConverter, ObjectToObjectConverter
    static class FooGenericConverter implements GenericConverter {
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Stream.of(new ConvertiblePair(String.class, Foo.class), new ConvertiblePair(String.class, Foo.class))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            int id = Integer.parseInt(((String) source));
            return new Foo(id, "name-" + id);
        }
    }

    @Test
    void name() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new FooGenericConverter());

        Foo foo = conversionService.convert("123", Foo.class);

        assertEquals(123, foo.getId());
        assertEquals("name-123", foo.getName());
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
