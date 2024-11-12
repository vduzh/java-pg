package by.duzh.springframework.context.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MultipleInitMethodsCallbackBean implements InitializingBean, DisposableBean {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @PostConstruct
    public void postConstructInitializer() throws Exception {
        value += 1;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        value += 2;
    }

    public void initMethod() throws Exception {
        value += 3;
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        value = "5";
    }

    @Override
    public void destroy() throws Exception {
        value += "5";
    }

    public void destroyMethod() throws Exception {
        if (!value.equals("56")) {
            throw new RuntimeException();
        }
    }
}
