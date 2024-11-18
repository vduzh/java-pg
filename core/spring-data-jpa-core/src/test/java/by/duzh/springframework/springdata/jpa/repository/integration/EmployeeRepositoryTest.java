package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.*;
import by.duzh.springframework.springdata.jpa.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findAll() {
        var repositories = repository.findAll();
        System.out.println(repositories);
//        assertThat(repositories).hasSize(1);
    }

    @Test
    void getById() {
//        var employee = repository.findById(1);
//        assertTrue(employee.isPresent());
    }

    @Test
    @Commit
    void add() {
        var employee = Employee.builder()
                .status(Status.A)
                .login("test")
                .personalInfo(PersonalInfo.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .birthDate(LocalDate.of(2020, 2, 22))
                        .build())
                .email("test@email.com")
                .startDate(LocalDate.of(2020, 1, 8))
                .office(Office.builder().id(1).build())
                .position(Position.builder().id(1).build())
                .build();

        var profile = Profile.builder()
                .language("english")
                .build();

        profile.setEmployee(employee);

        employee = repository.saveAndFlush(employee);
        System.out.println(employee);
    }
}
