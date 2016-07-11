package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.AbstractSpringTest;
import es.amplia.commons.mongodb.builders.AddressBuilder;
import es.amplia.commons.mongodb.builders.CountryBuilder;
import es.amplia.commons.mongodb.builders.PersonBuilder;
import es.amplia.commons.mongodb.model.Country;
import es.amplia.commons.mongodb.model.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PersonRepositoryTest extends AbstractSpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CountryRepository countryRepository;

    private List<Country> countries;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        countries = given_a_list_of_countries_persisted_in_mongo(100);
    }

    @Test
    public void given_a_person_when_inserted_assert_inserted_document_is_the_original_person() {
        Person person = given_a_person_persisted_in_mongo();

        Person insertedPerson = personRepository.findOne(person.getId());
        assertThat(insertedPerson, is(person));
    }

    @Test
    public void given_a_person_persisted_in_mongo_when_subsequent_updates_are_done_then_version_assures_no_data_is_lost() {
        expectedException.expect(OptimisticLockingFailureException.class);

        Person persontoUpdate = given_a_person_persisted_in_mongo();
        Person insertedPerson = personRepository.findOne(persontoUpdate.getId());

        assertThat(persontoUpdate.getVersion(), is(0L));
        assertThat(insertedPerson.getVersion(), is(0L));

        persontoUpdate.setAge(persontoUpdate.getAge() + 1);
        personRepository.save(persontoUpdate);

        assertThat(insertedPerson.getVersion(), is(0L));
        assertThat(persontoUpdate.getVersion(), is(1L));

        // OptimisticLockingFailureException
        personRepository.save(insertedPerson);
    }

    private Person given_a_person_persisted_in_mongo() {
        Person person = personRepository.insert(given_a_person());
        LOGGER.debug("{} inserted in mongoDB", person);
        return person;
    }

    private Person given_a_person() {
        return (Person) PersonBuilder.builder()
                .name("person_name")
                .lastName("person_lastname")
                .address(AddressBuilder.builder()
                        .address("person_address")
                        .city("person_city")
                        .country(countries.get(ThreadLocalRandom.current().nextInt(countries.size())))
                        .build())
                .age(new Random().nextInt(95))
                .salary(ThreadLocalRandom.current().nextLong(100000))
                .birthDate(
                        ThreadLocalRandom.current().nextInt(1940, 2016),
                        ThreadLocalRandom.current().nextInt(1, 13),
                        ThreadLocalRandom.current().nextInt(1, 29))
                .build();
    }

    private List<Country> given_a_list_of_countries_persisted_in_mongo(int size) {
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            countries.add(
                    (Country) CountryBuilder.builder()
                            .country(String.format("country_%03d", i))
                            .countryISO(randomAlphabetic(2)).build()
            );
        }
        return countryRepository.save(countries);
    }
}
