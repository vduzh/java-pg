package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {
    @Query("from Country c")
    List<Country> getAllHQL();

    @Query("from Country c where c.name = :name")
    List<Country> getAllByNameHQL(String name);

    // TODO: How???
    @Query("from Country c order by c.name")
    List<Country> getListCountriesOrderedByNameHQL(int limit);
}
