package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Integer> {
}
