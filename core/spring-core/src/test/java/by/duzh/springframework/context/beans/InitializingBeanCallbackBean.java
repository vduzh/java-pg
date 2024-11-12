package by.duzh.springframework.context.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class InitializingBeanCallbackBean implements InitializingBean, DisposableBean {
    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name is empty!");
        }

        if (value == null) {
            value = "default2";
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass() + ": destroying...");
    }
}
