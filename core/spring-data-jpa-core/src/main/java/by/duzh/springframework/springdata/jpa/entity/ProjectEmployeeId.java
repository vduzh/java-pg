package by.duzh.springframework.springdata.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * This is a composite key for the ProjectEmployee entity.
 * <p>
 * Must be Serializable as it is a key.
 * <p>
 * Usage:
 * <pre>
 * var key = ProjectEmployeeId.builder()
 *      .projectId(1L)
 *      .employeeId(2L)
 *      .build();
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ProjectEmployeeId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer projectId;

    private Long employeeId;
}


