package by.duzh.springframework.beans.factory.beans;

public class Foo {
    private String name;

    public Foo() {
        this.name = "default";
    }

    public Foo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
