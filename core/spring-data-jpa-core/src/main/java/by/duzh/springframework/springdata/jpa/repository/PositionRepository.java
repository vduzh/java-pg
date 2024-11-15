package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Position;
import org.springframework.data.repository.CrudRepository;

/**
 * NOTE: I use CrudRepository but JpaRepository is preferred.
 */
public interface PositionRepository extends CrudRepository<Position, Integer> {
}
