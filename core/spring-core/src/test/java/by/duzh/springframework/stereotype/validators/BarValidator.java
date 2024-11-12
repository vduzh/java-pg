package by.duzh.springframework.stereotype.validators;

import by.duzh.springframework.stereotype.beans.Bar;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BarValidator implements Validator {
    public BarValidator() {
        System.out.println("BarValidator created");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("BarValidator supports called");
        System.out.println("BarValidator supports result: " + Bar.class.equals(clazz));
        return Bar.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("BarValidator validate called");
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        Bar bar = (Bar) target;
        if (bar.name.length() == 1) {
            errors.rejectValue("name", "tooshort");
        }
    }
}
