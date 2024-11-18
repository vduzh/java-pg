package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Many-to-Many relationship with unidirectional associations.
 * <p/>
 * Many instances of an entity are associated with many instances of another entity.
 * <p/>
 * Book and Author. Each Book can have multiple Authors, and each Author can write multiple Books.
 */
@Data
@ToString(exclude = "writers")
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "foo")
public class Work implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "works")
    private Set<Writer> writers = new HashSet<>();

    public void addWriter(Writer author) {
        writers.add(author);
        author.getWorks().add(this);
    }
}
