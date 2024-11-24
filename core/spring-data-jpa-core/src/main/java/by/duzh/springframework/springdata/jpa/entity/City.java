package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * One-To-Many relationship with bidirectional associations.
 * <p/>
 * other entity has a collection of references to the first entity.
 * <p/>
 * Each Country has many Cities, but each City belongs to one Country only.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = {"country"})
@Builder
@Entity
@Table(name = "detail")
public class City implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "master_id")
    private Country country;
}
