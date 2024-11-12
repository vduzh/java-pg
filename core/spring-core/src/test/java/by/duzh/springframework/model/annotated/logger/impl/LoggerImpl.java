package by.duzh.springframework.model.annotated.logger.impl;

import by.duzh.springframework.model.annotated.logger.Logger;
import org.springframework.stereotype.Component;

@Component("logger")
public class LoggerImpl implements Logger {
    @Override
    public void log(String s) {
        System.out.printf("%s: %s%n", getClass().getName(), s);
    }
}
