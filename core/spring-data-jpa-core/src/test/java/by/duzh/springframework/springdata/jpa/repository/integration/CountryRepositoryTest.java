package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Country;
import by.duzh.springframework.springdata.jpa.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class CountryRepositoryTest {
    @Autowired
    private CountryRepository repository;

    @Test
    void findAll() {
        var teams = repository.findAll();
        assertThat(teams).hasSize(2);
    }

    @Test
    void getById() {
        var employee = repository.findById(1);
        assertTrue(employee.isPresent());
    }

    @Test
    void crate() {
        var team = Country.builder().name("Yellow").build();
        repository.save(team);
    }

    @Test
    @Commit
    void addEmployee() {
        // Get Employee
//        Employee employee

//        var employee = Employee.builder()
//                .status(Status.A)
//                .login("test")
//                .personalInfo(PersonalInfo.builder()
//                        .firstName("John")
//                        .lastName("Dow")
//                        .birthDate(LocalDate.of(2020, 2, 22))
//                        .build())
//                .email("test@email.com")
//                .startDate(LocalDate.of(2020, 1, 8))
//                .office(Office.builder().id(1).build())
//                .position(Position.builder().id(1).build())
//                .build();
//
//        var profile = Profile.builder()
//                .language("english")
//                .build();
//
//        profile.setEmployee(employee);
//
//        employee = repository.saveAndFlush(employee);
//        System.out.println(employee);
    }
}
