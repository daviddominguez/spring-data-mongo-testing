package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.AbstractSpringTest;
import es.amplia.commons.mongodb.builders.CountryBuilder;
import es.amplia.commons.mongodb.model.Country;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CountryRepositoryTest extends AbstractSpringTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void given_a_collection_of_countries_when_inserted_then_all_countries_are_inserted() {
        Collection<Country> countriesToInsert = given_a_collection_of_countries(100);
        countryRepository.save(countriesToInsert);
        List<Country> insertedCountries = countryRepository.findAll();
        assertThat(insertedCountries, is(countriesToInsert));
    }

    private List<Country> given_a_list_of_countries_persisted_in_mongo(int size) {
        return countryRepository.save(given_a_collection_of_countries(size));
    }

    private Collection<Country> given_a_collection_of_countries (int size) {
        List<Country> countries = new ArrayList<Country>();
        for (int i = 0; i < size; i++) {
            countries.add(
                    (Country) CountryBuilder.builder()
                            .country(String.format("country_%03d", i))
                            .countryISO(randomAlphabetic(2)).build()
            );
        }
        return countries;
    }
}
