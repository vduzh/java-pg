package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
