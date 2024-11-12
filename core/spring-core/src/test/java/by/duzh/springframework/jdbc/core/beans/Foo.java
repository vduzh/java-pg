package by.duzh.springframework.jdbc.core.beans;

public class Foo {
    public Long id;

    public String name;

    public Integer size;

    public Foo() {
    }

    public Foo(Long id, String name, Integer size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Foo(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Foo{" + "id=" + id + ", name='" + name + "', size=" + size + '}';
    }
}