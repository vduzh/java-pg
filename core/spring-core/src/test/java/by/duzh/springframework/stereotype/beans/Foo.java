package by.duzh.springframework.stereotype.beans;

import com.fasterxml.jackson.annotation.JsonView;

public class Foo {

    public interface WithoutId {};
    public interface WithId extends WithoutId {};

    public int id;

    public String name;

    public Foo() {
    }

    public Foo(String name) {
        this.name = name;
    }

    public Foo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonView(WithId.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonView(WithoutId.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
