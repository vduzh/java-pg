package by.duzh.springframework.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationContextAwareTest {

    public static class TestBean implements ApplicationContextAware {
        private ApplicationContext ctx;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.ctx = applicationContext;
        }

        public String getData() {
            // do some context relative operation
            return ctx.getBean(String.class);
        }
    }

    @Test
    public void applicationContextAware() throws Exception {
        try (var context = new GenericXmlApplicationContext(getClass(), "app-context-aware.xml")) {
            var bean = context.getBean(TestBean.class);
            assertEquals("test", bean.getData());
        }
    }
}