package by.duzh.springframework.beans.factory.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {
    // build an AnnotationConfigApplicationContext with the profiles ans configs
    private AnnotationConfigApplicationContext buildAnnotatedCtx(String[] profiles, Class<?>[] configs) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // activate profiles
        ctx.getEnvironment().setActiveProfiles(profiles);  // alt to -Dspring.profiles.active="profile1,profile2"
        // specify configs
        ctx.register(configs);
        ctx.refresh();
        return ctx;
    }

    // build an GenericXmlApplicationContext with the profiles ans configs
    private GenericXmlApplicationContext buildXMLCtx(String[] profiles, String[] locations) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        // activate profiles
        ctx.getEnvironment().setActiveProfiles(profiles);
        // specify configs
        ctx.load(locations);
        ctx.refresh();
        return ctx;
    }

    public interface Dao {
        String find(Integer id);
    }

    /**
     * Test a class level profile
     */
    @Repository
    @Profile("jdbc") // class-level config
    public static class JdbcDao implements Dao {
        @Override
        public String find(Integer id) {
            return "jdbc-" + id;
        }
    }

    @Repository
    @Profile("jpa") // class-level config
    public static class JpaDao implements Dao {
        @Override
        public String find(Integer id) {
            return "jpa-" + id;
        }
    }

    @Service("someService")
    public static class SomeService {
        @Autowired
        private Dao dao;

        public String show(Integer id) {
            return dao.find(id);
        }
    }

    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Dao.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SomeService.class)
            },
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JdoDao.class)
            }
    )
    public static class ClassLevelProfileConfig {
    }

    @Test
    public void testClassLevel() throws Exception {
        try (var ctx = buildAnnotatedCtx(new String[]{"jpa"},
                new Class<?>[]{ClassLevelProfileConfig.class})) {
            assertEquals("jpa-2", ctx.getBean("someService", SomeService.class).show(2));
        }

        try (var ctx2 = buildXMLCtx(new String[]{"jpa"},
                new String[]{"classpath:/**/app-context-profile-service.xml",
                        "classpath:/**/app-context-profile-class-jpa.xml",
                        "classpath:/**/app-context-profile-class-jdbc.xml"})) {
            assertEquals("jpa-2", ctx2.getBean("someService", SomeService.class).show(2));
        }
    }

    /**
     * Test Configuration level profile
     */
    @Repository
    public static class JdoDao implements Dao {
        @Override
        public String find(Integer id) {
            return "jdo-" + id;
        }
    }

    @Repository
    public static class HibernateDao implements Dao {
        @Override
        public String find(Integer id) {
            return "hibernate-" + id;
        }
    }

    @Profile("jdo")
    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JdoDao.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SomeService.class)
            }
    )
    public static class ConfigLevelProfileConfig1 {
    }

    @Profile("hibernate")
    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = HibernateDao.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SomeService.class)
            }
    )
    public static class ConfigLevelProfileConfig2 {
    }

    @Test
    public void testConfigLevel() throws Exception {
        try (var ctx = buildAnnotatedCtx(new String[]{"jdo"},
                new Class<?>[]{ConfigLevelProfileConfig1.class, ConfigLevelProfileConfig2.class})) {
            assertEquals("jdo-2", ctx.getBean("someService", SomeService.class).show(2));
        }

        try (var ctx2 = buildXMLCtx(new String[]{"jdo"}, new String[]{
                "classpath:/**/app-context-profile-service.xml", "classpath:/**/app-context-profile-config-jdo.xml"})) {
            assertEquals("jdo-2", ctx2.getBean("someService", SomeService.class).show(2));
        }
    }

    /**
     * Test a bean method level profile
     */
    public static class EjbDao implements Dao {
        @Override
        public String find(Integer id) {
            return "ejb-" + id;
        }
    }

    public static class TlinkDao implements Dao {
        @Override
        public String find(Integer id) {
            return "tlink-" + id;
        }
    }

    @Configuration
    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SomeService.class)
            }
    )
    public static class MethodLevelProfileConfig {
        @Bean
        @Profile("ejb")
        public Dao ejb() {
            return new EjbDao();
        }

        @Bean
        @Profile("tlink")
        public Dao tlink() {
            return new TlinkDao();
        }
    }

    @Test
    public void testMethodConfigLevel() throws Exception {
        try (var ctx = buildAnnotatedCtx(new String[]{"ejb"},
                new Class<?>[]{MethodLevelProfileConfig.class})) {
            assertEquals("ejb-2", ctx.getBean("someService", SomeService.class).show(2));
        }

        try (var ctx2 = buildXMLCtx(new String[]{"ejb"}, new String[]{
                "classpath:/**/app-context-profile-service.xml", "classpath:/**/app-context-profile-config-method.xml"})) {
            assertEquals("ejb-2", ctx2.getBean("someService", SomeService.class).show(2));
        }
    }

    @Configuration
    @ComponentScan(useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JdoDao.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SomeService.class)
            }
    )
    @Profile("default")
    public static class DefaultConfig {
        @Bean(name = "dao")
        public Dao jdoDao() {
            return new JdoDao();
        }
    }

    @Test
    void defaultProfile() {
        try (var ctx = buildAnnotatedCtx(new String[]{},
                new Class<?>[]{DefaultConfig.class})) {
            assertEquals("jdo-2", ctx.getBean("someService", SomeService.class).show(2));
        }

        try (var ctx2 = buildXMLCtx(new String[]{}, new String[]{
                "classpath:/**/app-context-profile-service.xml", "classpath:/**/app-context-profile-default.xml"})) {
            assertEquals("jdo-2", ctx2.getBean("someService", SomeService.class).show(2));
        }
    }

    /**
     * Test logical operations on profiles
     */
    @Configuration
    @Profile({"prod", "!jpa"})
    public static class ProdConfig {
        @Bean
        public String foo() {
            return "prod";
        }
    }

    @Configuration
    @Profile("dev & jpa")
    public static class DevConfig {
        @Bean
        public String foo() {
            return "dev";
        }
    }

    @Test
    void profileOperations() {
        try (var ctx = buildAnnotatedCtx(new String[]{"prod"},
                new Class<?>[]{ProdConfig.class, DevConfig.class})) {
            assertEquals("prod", ctx.getBean("foo", String.class));
        }

        try (var ctx = buildAnnotatedCtx(new String[]{"jdbc"},
                new Class<?>[]{ProdConfig.class, DevConfig.class})) {
            assertEquals("prod", ctx.getBean("foo", String.class));
        }

        try (var ctx = buildAnnotatedCtx(new String[]{"dev", "jpa"},
                new Class<?>[]{ProdConfig.class, DevConfig.class})) {
            assertEquals("dev", ctx.getBean("foo", String.class));
        }
    }
}