package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * NOTE: I use Repository but JpaRepository is preferred.
 */
public interface RoleRepository extends Repository<Role, Integer> {
    /**
     * Use the PartTreeJpaQuery
     */
    List<Role> findAllBy();

    Optional<Role> findFirstByOrderByNameAsc();

    List<Role> findTop2ByOrderByNameAsc();

    /**
     * Use the NamedQuery defined here {@link Role}
     */
    List<Role> findAllIgnoreCase(String name);

    /**
     * Use the SimpleJpaQuery with a HQL request
     */
    @Query("select r from Role r where r.id = :id")
    Optional<Role> findById(Integer id);

    /**
     * Use the NativeJpaQuery with SQL
     */
    @Query(
            nativeQuery = true,
            value = "SELECT r.* FROM role r where r.id = :identifier"
    )
    Optional<Role> findByIdentifier(Integer identifier);

    List<Role> findTop2By(Sort sort);

    List<Role> findAllBy(Pageable pageable);

    Slice<Role> findAllByNameNotContaining(String name, Pageable pageable);

    @Query(
            value = "select r from Role r where r.id > :id",
            countQuery = "select count(distinct r.id) from Role r where r.id > :id"
    )
    Page<Role> findAllByIdAfter(Integer id, Pageable pageable);
}
