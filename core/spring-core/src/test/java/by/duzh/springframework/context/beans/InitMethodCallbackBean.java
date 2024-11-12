package by.duzh.springframework.context.beans;

public class InitMethodCallbackBean {
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

    // init-method
    public void init() {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name is empty!");
        }

        if (value == null) {
            value = "default";
        }
    }

    //destroy method
    public void destroy() {
        System.out.println(this.getClass() + ": destroying...");
    }
}
