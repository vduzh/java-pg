package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project_employee")
public class ProjectEmployee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // composite key - better use a simple synthetic id
    @EmbeddedId
    private ProjectEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;
}
