package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// NOTE: NamedQuery queries must be inside an entity
@NamedQuery(
        // use Entity class name
        name = "Role.findAllIgnoreCase",
        // use HQL
        query = "select r from Role r where lower(r.name) = lower(:name)"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}
