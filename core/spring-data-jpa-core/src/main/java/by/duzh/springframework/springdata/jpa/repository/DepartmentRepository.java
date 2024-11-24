package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    @Query("from Department d")
    List<Department> findAllHQL();

    @Query("from Department d where d.name = :name")
    List<Department> findAllWithParamHQL(String name); // Option #1
    //List<Department> findAllWithParamHQL(@Param("name") String s); // Option #2
}
