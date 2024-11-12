package by.duzh.springframework.beans.factory.annotation;

import by.duzh.springframework.beans.factory.annotation.beans.scan.filtered.CustomFilter;
import by.duzh.springframework.beans.factory.annotation.beans.scan.filtered.Filtered;
import by.duzh.springframework.beans.factory.annotation.beans.scan.folder.ComponentScanTestBean;
import by.duzh.springframework.beans.factory.beans.Bar;
import by.duzh.springframework.beans.factory.beans.Foo;
import by.duzh.springframework.beans.factory.beans.InjectionsSample;
import by.duzh.springframework.context.ApplicationContextTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {
        "by.duzh.springframework.model.annotated.dao",
        "by.duzh.springframework.model.annotated.service",
        "by.duzh.springframework.model.annotated.beans",
        "by.duzh.springframework.model.annotated.logger",
        "by.duzh.springframework.beans.factory.annotation.beans.scan.filtered",
        "by.duzh.springframework.beans.factory.annotation.beans.scan.folder"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Filtered.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ComponentScanTestBean.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*SkipRegExed"),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = CustomFilter.class)
        }
)
@Import(ApplicationContextTest.AppConfig.class)
public class AnnotationConfigSample {
/*
    @Autowired
    public Foo foo;
*/

    /*
        @Autowired
        @Qualifier("greeting")
        private String parentGreeting;
    */
    @Autowired
    ApplicationContextTest.AppConfig configBasic;

    @Bean
    public String greeting() {
        return "Hi!";
    }

    @Bean
    public String parentGreeting() {
        //return parentGreeting;
        return "unknown";
    }

    @Bean
    public int constructorIntValue() {
        return 100;
    }

    @Bean
    public String male() {
        return "MALE";
    }

    @Bean
    public String female() {
        return "FEMALE";
    }

    /*
        It would be better to use XML config for:
            - data sources
            - transactions
            - JMS factory
            - JMX factory
    */

    @Bean
    public InjectSimpleConfig injectSimpleConfig() {
        InjectSimpleConfig config = new InjectSimpleConfig();
        config.setName("Test");
        config.setAge(18);
        return config;
    }

    /*It would be better to use annotations for components below*/
    @Bean
    public InjectionsSample injectionsSample() {
        //InjectionsSample sample = new InjectionsSample(foo, 123) {
        InjectionsSample sample = new InjectionsSample(null, 123) {
            @Override
            public Bar getBar() {
                //return configBasic.bar();
                return null;
            }

            @Override
            public String getReplacedValue() {
                return "replaced";
            }
        };
        //sample.setPropertyValue(foo);
        sample.setPropertyStringValue("test");
        sample.setSpelString(injectSimpleConfig().getName());
        return sample;
    }
}
