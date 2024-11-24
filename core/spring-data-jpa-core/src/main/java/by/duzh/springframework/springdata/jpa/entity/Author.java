package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Many-to-Many relationship with unidirectional associations.
 * <p/>
 * Many instances of an entity are associated with many instances of another entity.
 * <p/>
 * Book and Author. Each Book can have multiple Authors, and each Author can write multiple Books.
 */
@Data
@ToString
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bar")
public class Author implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
