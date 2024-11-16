package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"office", "position", "manager"})
@Builder
@Entity
@Table(name = "employee")
public class Employee implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(unique = true, nullable = false)
    private String login;

    @Embedded // not required
    private PersonalInfo personalInfo;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate startDate;

    // optional = false -> inner join will be used that is faster than left outer
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
