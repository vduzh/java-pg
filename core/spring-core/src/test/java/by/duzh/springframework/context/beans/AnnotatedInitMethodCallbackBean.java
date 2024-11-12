package by.duzh.springframework.context.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class AnnotatedInitMethodCallbackBean {
    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @PostConstruct // dependency on javax.annotation required
    public void initializer() {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name is empty!");
        }

        if (value == null) {
            value = "default3";
        }
    }

    @PreDestroy
    public void destroyer() {
        System.out.println(this.getClass() + ": destroying...");
    }
}
