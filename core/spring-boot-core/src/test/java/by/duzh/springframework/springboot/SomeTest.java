package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@SpringBootTest(classes = SomeTest.SomeConfig.class)
public class SomeTest {

    @TestConfiguration
    static class SomeConfig {
        public String foo() {return "foo";}
    }

    @Autowired
    String foo;

    @Test
    void name() {
        System.out.println(foo);
        //throw new RuntimeException();
    }
}
