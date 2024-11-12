package by.duzh.springframework.beans.factory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeanNameAwareTest {
    public static class BeanNameAwareBean implements BeanNameAware {
        private String name;

        @Override
        public void setBeanName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private GenericXmlApplicationContext buildGenericXmlApplicationContext() throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("classpath:context/app-context-bean-name-aware.xml");
        context.refresh();
        return context;
    }

    @Test
    public void beanNameAware() throws Exception {
        try (GenericXmlApplicationContext context = buildGenericXmlApplicationContext()) {
            var bean = context.getBean(BeanNameAwareBean.class);
            assertEquals("nameAware", bean.getName());
        }
    }
}