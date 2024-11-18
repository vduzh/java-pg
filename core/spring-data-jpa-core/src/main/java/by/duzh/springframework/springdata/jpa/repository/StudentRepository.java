package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("select s from Student s")
    List<Student> findAllHQL();

    // implicit join
    @Query("from Student s where s.school.name = :schoolName")
    List<Student> findAllBySchoolHQL(String schoolName);

    // explicit join - BETTER!!!
    @Query("from Student s join s.school c where c.name = :schoolName")
    List<Student> findAllBySchoolWithJoinHQL(String schoolName);

    @Query("from Student s left join s.school c where c.name = :schoolName")
    List<Student> findAllBySchoolWithLeftJoinHQL(String schoolName);

    @Query("select s from Student s order by s.name")
    List<Student> findAllOrderByHQL();

    @Query("from Student s join s.school c order by c.name desc")
    List<Student> findAllBySchoolWithJoinAndOrderByHQL();

    @Modifying
    @Query("update Student s set s.name = :name where s.id = :id")
    int updateStudentNameHQL(Integer id, String name);
}
