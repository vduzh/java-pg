package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * One-To-Many relationship with bidirectional associations.
 * <p/>
 * other entity has a collection of references to the first entity.
 * <p/>
 * Each Country has many Cities, but each City belongs to one Country only.
 */
@Data
@ToString(exclude = "cities")
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "master")
public class Country implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "country")
    private Set<City> cities = new HashSet<>();

    public void addCity(City city) {
        city.setCountry(this);
        cities.add(city);
    }
}
