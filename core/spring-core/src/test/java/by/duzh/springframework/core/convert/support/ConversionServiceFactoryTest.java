package by.duzh.springframework.core.convert.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(ConversionServiceFactoryTest.TestConfig.class)
public class ConversionServiceFactoryTest {
    private static final Logger logger = Logger.getLogger(ConversionServiceFactoryTest.class.getName());

    @Configuration
    static class TestConfig {
        @Bean
        public ConversionServiceFactoryBean conversionService() {
            ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
            // Register custom converter
            factoryBean.setConverters(Collections.singleton(new Converter<Integer, StringBuilder>() {
                @Override
                public StringBuilder convert(Integer source) {
                    return new StringBuilder().append(source);
                }
            }));
            return factoryBean;
        }
    }

    @Test
    void test(@Autowired ConversionService conversionService) {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
