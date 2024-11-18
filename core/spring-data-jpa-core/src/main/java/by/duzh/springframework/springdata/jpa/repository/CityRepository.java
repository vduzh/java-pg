package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    @Query("select ci from Country cn join cn.cities ci where cn.name = :country")
    List<City> getAllByCountryNameHQ(String country);

    @Query("from City ci join ci.country cn where cn.name = :country")
    List<City> getAllByCountryName2HQ(String country);

    @Query("from City ci join ci.country cn where cn.name = :country order by ci.name")
    List<City> getAllByCountryNameOrderedByCityNameHQ(String country);

    @Query("select count(c) from City c")
    List<City> getCountOfCitiesHQ();
}
