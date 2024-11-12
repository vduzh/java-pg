package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig
public class AutowiredTest {

    @Component("testBean")
    static class TestBean {
        public int constructorValue;

        public int propertyValue;

        @Autowired(required = false)
        public Date created;

        // Step 1: Autowiring by type
        @Autowired
        public int fieldValue;

        // Autowiring by type
        @Autowired
        public TestBean(int constructorValue) {
            this.constructorValue = constructorValue;
        }

        // This constructor is not used by String (@Autowired is used)
        public TestBean() {
        }

        // Autowiring by type
        @Autowired
        public void setPropertyValue(int propertyValue) {
            this.propertyValue = propertyValue;
        }

        // Step 2: There are more than 2 beans of the same type is available. Then @Primary is used.
        @Autowired
        public long distance;

        // Step 3: @Qualifier
        @Autowired
        @Qualifier("longValue2")
        public long length;

        // Step 4: Autowiring by name
        @Autowired
        public String male;
    }

    @Component("testBean2")
    static class TestBean2 {
        public int intValue;

        // @Autowired could be skipped for a class with one constructor
        public TestBean2(Integer intValue) {
            this.intValue = intValue;
        }

        @Autowired
        @Value("#{testBean.male}")
        public String spelString;
    }

    @Component("testBean3")
    static class TestBean3 {
        public String strValue;
        public Integer intValue;
        public Optional<Double> optionalDouble;

        public void foo(@Autowired(required = false) Integer intValue, @Nullable String strValue,
                         Optional<Double> optionalDouble) {
            this.intValue = intValue;
            this.strValue = strValue;
            this.optionalDouble = optionalDouble;
        }
    }

    @Configuration
    @ComponentScan(
            useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestBean.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestBean2.class)
            }
    )
    static class AppConfig {
        @Bean
        public Integer intValue() {
            return 1;
        }

        // Two beans of the same type (String) -> will be resolved by @Primary
        @Bean
        @Primary
        public Long longValue() {
            return 1_000L;
        }

        @Bean
        public Long longValue2() {
            return 2_000L;
        }

        // Two beans of the same type (String) -> will be resolved by name
        @Bean
        public String male() {
            return "MALE";
        }

        @Bean
        public String female() {
            return "FEMALE";
        }

        @Bean
        @Autowired
        public String foo(String female) {
            return female;
        }
    }

    @Autowired
    private TestBean bean;

    @Autowired
    private TestBean2 bean2;

    @Autowired
    private TestBean3 bean3;

    @Autowired
    private String foo;

    @Test
    void test() {
        // Not required
        assertNull(bean.created);

        // Inject by type
        assertEquals(1, bean.constructorValue);
        assertEquals(1, bean.fieldValue);
        assertEquals(1, bean.propertyValue);

        // Inject by @Primary
        assertEquals(1_000, bean.distance);

        // Inject by @Qualifier
        assertEquals(2_000, bean.length);

        // Inject by field name
        assertEquals("MALE", bean.male);

        assertEquals(1, bean2.intValue);
        assertEquals("MALE", bean2.spelString); // SPEL is used

        // Inject into config class
        assertEquals("FEMALE", foo);

        assertNull(bean3.intValue);
        assertNull(bean3.strValue);
        assertNull(bean3.optionalDouble);
    }

    @Test
    void name() throws Exception {
        throw new RuntimeException("test collection injection!!!");
    }
}
