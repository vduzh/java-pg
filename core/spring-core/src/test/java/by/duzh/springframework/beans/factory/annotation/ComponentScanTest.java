package by.duzh.springframework.beans.factory.annotation;

import by.duzh.springframework.beans.factory.annotation.beans.scan.filtered.*;
import by.duzh.springframework.beans.factory.annotation.beans.scan.folder.ComponentScanTestBean;
import by.duzh.springframework.beans.factory.annotation.beans.scan.folder.SomeBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

//Note: Look into https://www.baeldung.com/spring-componentscan-filter-type
public class ComponentScanTest {

    private ApplicationContext[] buildContexts(Class<?> config, String configLocation) {
        return new ApplicationContext[]{
                new AnnotationConfigApplicationContext(config),
                new GenericXmlApplicationContext(getClass(), configLocation)
        };
    }

    @ComponentScan(basePackages = "by.duzh.springframework.beans.factory.annotation.beans.scan")
    public static class BasePackagesConfig {
    }

    @ComponentScan(basePackageClasses = SomeBean.class)
    public static class BasePackageClassesConfig {
    }

    @Test
    void basePackages() throws Exception {
        for (var ctx : buildContexts(BasePackagesConfig.class, "app-context-base-packages.xml")) {
            ctx.getBean(ComponentScanTestBean.class);
            ctx.getBean(SomeBean.class);
        }

        for (var ctx : buildContexts(BasePackageClassesConfig.class, "app-context-base-packages.xml")) {
            ctx.getBean(ComponentScanTestBean.class);
            ctx.getBean(SomeBean.class);
        }
    }

    @ComponentScan(basePackages = "by.duzh.springframework.beans.factory.annotation.beans.scan", useDefaultFilters = false)
    public static class UseDefaultFiltersConfig {
    }

    @Test
    void useDefaultFiltersFalse() throws Exception {
        for (var ctx : buildContexts(UseDefaultFiltersConfig.class, "app-context-use-default-filters.xml")) {
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(ComponentScanTestBean.class));
        }
    }

    @ComponentScan(
            basePackages = "by.duzh.springframework.beans.factory.annotation.beans.scan",
            useDefaultFilters = false, // NOTE: Must to have just one bean!
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ComponentScanTestBean.class)
    )
    public static class IncludeFiltersConfig {
    }

    @Test
    void includeFilters() throws Exception {
        for (var ctx : buildContexts(IncludeFiltersConfig.class, "app-context-include-filters.xml")) {
            ctx.getBean(ComponentScanTestBean.class);
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(SomeBean.class));
        }
    }

    @ComponentScan(
            basePackages = "by.duzh.springframework.beans.factory.annotation.beans.scan",
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Filtered.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SomeBean.class),
                    @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*RegExpFiltered"),
                    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = CustomFilter.class)
            }
    )
    public static class ExcludeFiltersConfig {
    }

    @Test
    void excludeFilters() throws Exception {
        for (var ctx : buildContexts(ExcludeFiltersConfig.class, "app-context-exclude-filters.xml")) {
            ctx.getBean(ComponentScanTestBean.class);
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(FilterAnnotated.class));
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(SomeBean.class));
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(RegExpFiltered.class));
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(CustomFiltered.class));
        }
    }

    public interface Animal {
    }

    @Component
    public static class Elephant implements Animal {
    }

    @Component
    public static class Cat implements Animal {
    }

    @ComponentScan(
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Animal.class)
    )
    public static class ComponentScanAssignableTypeFilterApp {
    }

    @Test
    public void whenAssignableTypeFilterIsUsed_thenComponentScanShouldRegisterBean() {
        var applicationContext = new AnnotationConfigApplicationContext(ComponentScanAssignableTypeFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework") && !bean.contains("ComponentScanAssignableTypeFilterApp"))
                .collect(Collectors.toList());
        assertEquals(beans.size(), 2);
        assertTrue(beans.contains("componentScanTest.Cat"));
        assertTrue(beans.contains("componentScanTest.Elephant"));
    }
}