package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Role;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface RoleRepository extends Repository<Role, Integer> {
    Optional<Role> findById(Integer id);

    void delete(Role role);
}
