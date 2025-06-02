package by.duzh.springframework.validation;

import by.duzh.springframework.validation.beans.Person;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.logging.Logger;

public class ValidatorTest {
    private static final Logger logger = Logger.getLogger(ValidatorTest.class.getName());

    private static class PersonValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            //Person.class.isAssignableFrom(clazz);
            return Person.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
            Person p = (Person) target;
            if (p.getAge() < 0) {
                errors.rejectValue("age", "negativevalue");
            } else if (p.getAge() > 110) {
                errors.rejectValue("age", "too.darn.old");
            }
        }
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
