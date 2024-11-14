package by.duzh.springframework.springdata.jpa.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    Integer getId();

    void setId(Integer id);
}
