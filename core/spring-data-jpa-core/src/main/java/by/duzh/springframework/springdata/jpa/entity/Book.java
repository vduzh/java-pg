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
@ToString(exclude = "authors")
@EqualsAndHashCode(of = "title")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "foo")
public class Book implements BaseEntity<Integer> {

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
    private Set<Author> authors = new HashSet<>();

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
