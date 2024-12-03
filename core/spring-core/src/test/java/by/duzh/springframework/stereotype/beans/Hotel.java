package by.duzh.springframework.stereotype.beans;


import jakarta.validation.constraints.NotEmpty;

public class Hotel {
    public Integer id;

    @NotEmpty
    public String name;

    public Hotel() {
    }

    public Hotel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hotel(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
