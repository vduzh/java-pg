package by.duzh.springframework.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: It is often desirable to call setWebApplicationType(WebApplicationType.NONE) when using SpringApplication
// within a JUnit test
public class SpringApplicationTest {

    @Configuration
    @EnableAutoConfiguration
    static class AutoConfiguredPrimarySource {
    }

    @Test
    void testRunWithAutoConfig() {
        ConfigurableApplicationContext ctx = SpringApplication.run(AutoConfiguredPrimarySource.class);

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Configuration
    static class PrimarySource {
    }

    @Test
    void testConstructor() {
        SpringApplication app = new SpringApplication(PrimarySource.class);
        app.setWebApplicationType(WebApplicationType.NONE); // AnnotationConfigApplicationContext will be used
        ConfigurableApplicationContext ctx = app.run();

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Test
    void testSetApplicationContextFactory() {
        SpringApplication app = new SpringApplication();
        app.addPrimarySources(Collections.singleton(PrimarySource.class));
        app.setApplicationContextFactory(webAppType -> new AnnotationConfigApplicationContext());
        ConfigurableApplicationContext ctx = app.run();

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Test
    void testBuilder() {
        new SpringApplicationBuilder()
                .sources(PrimarySource.class)
                .web(WebApplicationType.NONE)
                .run();
    }

    @Test
    void runWithArguments() {
        var ctx = new SpringApplicationBuilder(PrimarySource.class)
                .web(WebApplicationType.NONE)
                .run("--aaa=bbb");

        var args = ctx.getBean(ApplicationArguments.class);
        assertEquals("bbb", args.getOptionValues("aaa").get(0));
    }

    @Component
    static class PropertyTestComponent {
        // Default properties: SpringApplication.setDefaultProperties
        @Value("${go.to}")
        public String goTo;

        // @PropertySource
        @Value("${custom.user}")
        public String customUser;

        // application.properties
        @Value("${some.prop}")
        public String someProp;

        // application.yml
        @Value("${acme.name}")
        public String acmeName;

        // RandomValuePropertySource
        @Value("${custom.secret}")
        public String customSecret;

        // OS Env
        @Value("${JAVA_HOME}")
        public String javaHome;

        // Java System properties (System.getProperties()).
        @Value("${java.vm.specification.vendor}")
        public String vendor;

        // JNDI attributes from java:comp/env.

        // ServletContext init parameters.

        // ServletConfig init parameters.

        // Properties from SPRING_APPLICATION_JSON
        //@Value("${acme.name}")
        //public String acmeName;

        // Command line arguments
        @Value("${cmd.zip}")
        public String cmdZip;

        // properties attribute on your tests

        // @TestPropertySource annotations on your tests.

        // Devtools global settings properties in the $HOME/.config/spring-boot directory
    }

    @Configuration
    @PropertySource("classpath:custom.properties")
    static class PropertyTestConfig {
    }

    @Test
    void testProperties() {
        var ctx = new SpringApplicationBuilder()
                .sources(PrimarySource.class)
                .child(PropertyTestConfig.class, PropertyTestComponent.class)
                .web(WebApplicationType.NONE)
                .properties("go.to=Berlin") // setDefaultProperties
                .run("--cmd.zip=220055");

        var cmp = ctx.getBean(PropertyTestComponent.class);
        assertEquals("Berlin", cmp.goTo);
        assertEquals("admin", cmp.customUser);
        assertEquals("test", cmp.someProp);
        assertEquals("Learning Spring Boot", cmp.acmeName);
        assertNotNull(cmp.customSecret);
        assertNotNull(cmp.javaHome);
        assertEquals("Oracle Corporation", cmp.vendor);
        //assertEquals("test", cmp.acmeName);
        assertEquals("220055", cmp.cmdZip);
    }

    @Test
    void setAdditionalProfiles() {
        var ctx = new SpringApplicationBuilder()
                .sources(PrimarySource.class)
                .child(PropertyTestConfig.class, PropertyTestComponent.class)
                .profiles("dev")
                .web(WebApplicationType.NONE)
                .run();

        var cmp = ctx.getBean(PropertyTestComponent.class);
        assertEquals("DEV Learning Spring Boot", cmp.acmeName);
    }

    @Test
    void setAddCommandLineProperties()  {
        var ctx = new SpringApplicationBuilder()
                .sources(PrimarySource.class)
                .child(PropertyTestConfig.class, PropertyTestComponent.class)
                .web(WebApplicationType.NONE)
                .run("--cmd.zip=220055");

        var env = ctx.getBean(Environment.class);
        assertEquals("220055", env.getProperty("cmd.zip"));

        var cmp = ctx.getBean(PropertyTestComponent.class);
        assertEquals("220055", cmp.cmdZip);

        SpringApplication.exit(ctx);

        ctx = new SpringApplicationBuilder()
                .sources(PrimarySource.class)
                .child(PropertyTestConfig.class, PropertyTestComponent.class)
                .web(WebApplicationType.NONE)
                .addCommandLineProperties(false) //NOTE here!!!
                .run("--cmd.zip=220055");

        env = ctx.getBean(Environment.class);
        //assertNull(env.getProperty("cmd.zip"));

        cmp = ctx.getBean(PropertyTestComponent.class);
        assertEquals("220055", cmp.cmdZip);

        SpringApplication.exit(ctx);
    }
}
