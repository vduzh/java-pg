package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "login")
@ToString(exclude = {"employee"})
@Builder
@Entity
@Table(name = "profile")
public class Profile implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String language;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.setProfile(this);
    }

}
