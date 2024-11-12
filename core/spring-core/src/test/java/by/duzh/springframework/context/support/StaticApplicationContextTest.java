package by.duzh.springframework.context.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.StaticApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaticApplicationContextTest {
    static class Foo {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Test
    void test() throws Exception {
        try (var ctx = new StaticApplicationContext()) {
            ctx.registerSingleton("str", String.class);
            ctx.refresh();

            ctx.getBean("str");
        }

        try (var ctx = new StaticApplicationContext()) {
            MutablePropertyValues pvs = new MutablePropertyValues();
            pvs.add("value", "bar");
            ctx.registerSingleton("foo", Foo.class, pvs);
            ctx.refresh();

            assertEquals("bar", ctx.getBean("foo", Foo.class).getValue());
        }
    }
}
