package by.duzh.springframework.core.convert.converter;

import by.duzh.springframework.core.convert.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.logging.Logger;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConditionalGenericConverterTest {
    private static final Logger logger = Logger.getLogger(ConditionalGenericConverterTest.class.getName());

    /**
     * @see IdToEntityConverter
     */
    static class IdToFooConverter implements ConditionalGenericConverter {
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Stream.of(new ConvertiblePair(String.class, Foo.class), new ConvertiblePair(String.class, Foo.class))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            int id;

            if (sourceType.getType().equals(Integer.class)) {
                id = (Integer) source;
            } else {
                id = Integer.parseInt(((String) source));
            }
            return new Foo(id, "name-" + id);
        }

        @Override
        public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
            return true;
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
