package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.City;
import by.duzh.springframework.springdata.jpa.entity.City_;
import by.duzh.springframework.springdata.jpa.entity.Country;
import by.duzh.springframework.springdata.jpa.entity.Country_;
import by.duzh.springframework.springdata.jpa.repository.CityRepository;
import by.duzh.springframework.springdata.jpa.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class OneToManyBiTest {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;


    @Test
    void findAllCountries() {
        var countries = countryRepository.findAll();
        Assertions.assertThat(countries).hasSize(8);

        // Use a spec
        Specification<Country> spec = null;

        // find all
        spec = (root, query, builder) -> null;
        countries = countryRepository.findAll(spec);
        System.out.println(countries);
        Assertions.assertThat(countries).hasSize(8);

        // find by name
        spec = (root, query, builder) -> builder.equal(root.get(Country_.name), "Countries");
        countries = countryRepository.findAll(spec);
        System.out.println(countries);
        Assertions.assertThat(countries).hasSize(1);

        // find countries ordered by name
        spec = (root, query, builder) -> {
            if (query != null) {
                query.orderBy(builder.asc(root.get(Country_.name)));
            }
            return null;
        };
        countries = countryRepository.findAll(spec);
        System.out.println(countries);
        Assertions.assertThat(countries).hasSize(8);
    }

    @Test
    void findCities() {
        var cities = cityRepository.findAll();
        Assertions.assertThat(cities).hasSize(20);

        // Use a spec
        Specification<City> spec = null;

        // find all
        spec = (root, query, builder) -> null;
        cities = cityRepository.findAll(spec);
        Assertions.assertThat(cities).hasSize(20);

        // find by name
        spec = (root, query, builder) -> builder.equal(root.get(City_.name), "Minsk");
        cities = cityRepository.findAll(spec);
        Assertions.assertThat(cities).hasSize(1);

        // find all cities by country name
        spec = (city, query, builder) -> {
            var country = city.join(City_.country);
            return builder.equal(country.get(Country_.name), "Countries");
        };
        cities = cityRepository.findAll(spec);
        System.out.println(cities);
        Assertions.assertThat(cities).hasSize(3);
    }

    @Test
    void getCountryById() {
        var country = countryRepository.findById(1);
        assertTrue(country.isPresent());
    }

    @Test
    void getCityById() {
        var city = cityRepository.findById(1);
        assertTrue(city.isPresent());
    }

    @Test
    void addNewCountryWithNewCity() {
        var city = City.builder().name("Test City").build();

        var country = Country.builder().name("Test Country").build();
        country.addCity(city);

        var res = countryRepository.save(country);
        assertNotNull(res.getId());
    }

    @Test
    void addNewCityWithNewCountry() {
        // TODO: fix this test
        var city = City.builder().name("Test City").build();
        var country = Country.builder()
                .name("Test Country")
                .build();
        country.addCity(city);

        var res = cityRepository.save(city);
        assertNotNull(res.getId());
    }

    @Test
    void addNewCityToTheCountry() {
        var city = City.builder().name("Test City").build();

        var countryOpt = countryRepository.findById(7);
        assertTrue(countryOpt.isPresent());

        var country = countryOpt.get();
        country.addCity(city);

        var res = countryRepository.save(country);
        assertNotNull(res.getId());
    }

    @Test
    void addNewCountryToTheCity() {
        var country = Country.builder().name("Test Country").build();

        var cityOpt = cityRepository.findById(19);
        assertTrue(cityOpt.isPresent());

        var city = cityOpt.get();
        city.setCountry(country);

        var res = cityRepository.save(city);
        assertNotNull(res.getId());
    }
}