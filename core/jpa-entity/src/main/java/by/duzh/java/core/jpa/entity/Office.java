package by.duzh.java.core.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employees"})
@EqualsAndHashCode(of = "name")
@Builder
@Entity
@Table(name = "office")
public class Office implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Office parent;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private Employee manager;
//
//    @Builder.Default  // to set default value
//    // for bi-direction
//    @OneToMany(mappedBy = "office",
//            // when we do something with office
//            cascade = CascadeType.REMOVE,
//            // when we delete employee from the employees collection
//            orphanRemoval = true
//    )
//    // for uni direction.
//    // @JoinColumn(name = "office_id")
//    private Set<Employee> employees = new HashSet<>(); // prevent NPE for a new office
//
//    public void addEmployees(Employee employee) {
//        employees.add(employee);
//        employee.setOffice(this);
//    }
}
