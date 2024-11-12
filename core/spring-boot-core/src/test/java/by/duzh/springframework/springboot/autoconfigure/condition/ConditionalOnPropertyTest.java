package by.duzh.springframework.springboot.autoconfigure.condition;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {DefaultSpringBootConfiguration.class,ConditionalOnPropertyTest.TestCompAA.class,
        ConditionalOnPropertyTest.TestCompBB.class}, args = "--aa=aaa")
public class ConditionalOnPropertyTest {

    @ConditionalOnProperty("aa")
    @Component("testA")
    static class TestCompAA {}

    @ConditionalOnProperty("bb")
    @Component("testB")
    static class TestCompBB {}

    @Autowired
    ApplicationContext ctx;

    @Test
    void name() {
        assertTrue(ctx.containsBean("testA"));
        assertFalse(ctx.containsBean("testB"));
    }
}
