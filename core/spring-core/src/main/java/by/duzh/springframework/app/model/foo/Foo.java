package by.duzh.springframework.app.model.foo;

import jakarta.persistence.*;

@Entity
@Table(name = "t_foo")
public class Foo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer size;

    public Foo() {
    }

    public Foo(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public Foo(Long id, String name, Integer size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Foo{" + "id=" + id + ", name='" + name + "', size=" + size + '}';
    }
}