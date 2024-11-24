package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * One-To-Many relationship with unidirectional associations.
 * <p/>
 * Entity has a reference to one or many instances of another entity
 * <p/>
 * Each Department has many Teachers, but eachTeacher belongs to one Department only.
 */
@Data
@ToString
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detail")
public class Teacher implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
