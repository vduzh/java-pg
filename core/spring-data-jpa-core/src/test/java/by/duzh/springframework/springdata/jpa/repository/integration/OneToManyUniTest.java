package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Department;
import by.duzh.springframework.springdata.jpa.entity.Teacher;
import by.duzh.springframework.springdata.jpa.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class OneToManyUniTest {
    @Autowired
    private DepartmentRepository repository;

    @Test
    void findAll() {
        var departments = repository.findAll();
        assertThat(departments).hasSize(8);
    }

    @Test
    void getById() {
        var department = repository.findById(1);
        assertTrue(department.isPresent());
    }

    @Test
    void crateDepartment() {
        var team = Department.builder().name("Test").build();
        repository.save(team);
    }

    @Test
    void crateDepartmentWithNewTeacher() {
        var department = Department.builder().name("Test").build();
        department.addTeacher(Teacher.builder().name("Foo").build());

        repository.save(department);
    }

    @Test
    void addTeacher() {
        var departmentOpt = repository.findById(1);
        assertTrue(departmentOpt.isPresent());

        var department = departmentOpt.get();

        department.addTeacher(Teacher.builder().name("Foo").build());
        department.addTeacher(Teacher.builder().name("Bar").build());

        repository.save(department);
    }
}
