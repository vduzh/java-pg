package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * One-To-Many relationship with unidirectional associations.
 * <p/>
 * Entity has a reference to one or many instances of another entity
 * <p/>
 * Each Department has many Teachers, but eachTeacher belongs to one Department only.
 */
@Data
@ToString(exclude = "teachers")
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "master")
public class Department implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Builder.Default
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany
    @JoinColumn(name = "master_id")
    private Set<Teacher> teachers = new HashSet<>();

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
}
