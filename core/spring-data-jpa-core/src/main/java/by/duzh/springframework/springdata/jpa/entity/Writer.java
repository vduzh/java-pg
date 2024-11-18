package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Many-to-Many relationship with bidirectional associations.
 * <p/>
 * Each entity involved will have a collection of references to the other entity.
 * <p/>
 * Student entity that has a collection of Course entities and a Course entity that in turn has a collection of
 * Student entities.
 */
@Data
@ToString
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bar")
public class Writer implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "foo_bar",
            joinColumns = @JoinColumn(name = "foo_id"),
            inverseJoinColumns = @JoinColumn(name = "bar_id")
    )
    private Set<Work> works = new HashSet<>();

    public void addWork(Work work) {
        works.add(work);
        work.getWriters().add(this);
    }
}
