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
@Table(name = "author")
public class AuthorBi implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<BookBi> books = new HashSet<>();

    public void addBook(BookBi book) {
        books.add(book);
        book.getAuthors().add(this);
    }
}
