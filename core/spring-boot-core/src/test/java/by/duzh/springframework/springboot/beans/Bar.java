package by.duzh.springframework.springboot.beans;

import org.springframework.stereotype.Component;

@Component
public class Bar {
    public String value() {
        System.out.println(getClass() + ".foo is working");

        return "bar";
    }
}
