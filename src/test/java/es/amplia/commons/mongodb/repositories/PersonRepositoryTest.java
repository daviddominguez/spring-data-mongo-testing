package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.AbstractSpringTest;
import es.amplia.commons.mongodb.builders.AddressBuilder;
import es.amplia.commons.mongodb.builders.CountryBuilder;
import es.amplia.commons.mongodb.builders.PersonBuilder;
import es.amplia.commons.mongodb.model.Country;
import es.amplia.commons.mongodb.model.Person;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PersonRepositoryTest extends AbstractSpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CountryRepository countryRepository;

    private List<Country> countries;
    private List<String> tags;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        countries = given_a_list_of_countries_persisted_in_mongo(10);
        tags = given_a_list_of_tags();
    }

    @Test
    public void given_a_person_when_inserted_assert_inserted_document_is_the_original_person() {
        Person person = given_a_list_of_people_persisted_in_mongo(1).get(0);

        Person insertedPerson = personRepository.findOne(person.getId());
        assertThat(insertedPerson, is(person));
    }

    @Test
    public void given_people_inserted_in_db_when_findBySalaryBetween_invoked_then_people_returned_has_their_salary_between_passed_values() {
        given_a_list_of_people_persisted_in_mongo(100);
        BigDecimal from = new BigDecimal(1000);
        BigDecimal to = new BigDecimal(15000);
        List<Person> found = personRepository.findBySalaryBetween(from, to);
        LOGGER.debug("{} people found with salary between {} and {}", found.size(), from, to);
        for (Person person : found) {
            assertThat(person.getSalary(), allOf(greaterThanOrEqualTo(from), lessThanOrEqualTo(to)));
        }
    }

    @Test
    public void given_people_inserted_in_db_when_findByBirthDateAfter_invoked_then_people_returned_has_born_after_passed_values() {
        given_a_list_of_people_persisted_in_mongo(100);
        LocalDateTime birthDate = new LocalDateTime(2000, 1, 1, 0, 0);
        List<Person> found = personRepository.findByBirthDateAfter(birthDate);
        LOGGER.debug("{} people found with birthdate after {}", found.size(), birthDate);
        for (Person person : found) {
            assertTrue(person.getBirthDate().isAfter(birthDate));
        }
    }

    @Test
    public void given_people_inserted_in_db_when_findByAddressCountryIn_invoked_then_people_returned_lives_in_one_of_the_countries_passed() {
        given_a_list_of_people_persisted_in_mongo(100);
        Collection<ObjectId> countriesToSearchFor = asList(countries.get(0).getId(), countries.get(1).getId());
        List<Person> found = personRepository.findByAddressCountryIdIn(countriesToSearchFor);
        LOGGER.debug("{} people found with address country in {}", found.size(), countriesToSearchFor);
        for (Person person : found) {
            assertThat(person.getAddress().getCountry().getId(), isIn(countriesToSearchFor));
        }
    }

    @Test
    public void given_people_inserted_in_db_when_findByAddressTagsIn_invoked_then_people_returned_lives_in_one_of_the_countries_passed() {
        given_a_list_of_people_persisted_in_mongo(100);
        Collection<String> tagsToSearch = tags.subList(4, 6);
        List<Person> found = personRepository.findByAddressTagsIn(tagsToSearch);
        LOGGER.debug("{} people found with address tags in {}", found.size(), tagsToSearch);
        for (Person person : found) {
            assertTrue(CollectionUtils.containsAny(person.getAddress().getTags(), tagsToSearch));
        }
    }

    @Test
    public void given_people_inserted_in_db_when_findByFullname_invoked_then_all_people_with_that_name_and_lastName_is_returned() {
        given_a_list_of_people_persisted_in_mongo(100);
        String name = "person_name";
        String lastName = "person_lastname";
        List<Person> found = personRepository.findByFullname(name, lastName);
        LOGGER.debug("{} people found with {} as name and {} as lastName", found.size(), name, lastName);
        for (Person person : found) {
            assertThat(person.getName(), is(name));
            assertThat(person.getLastName(), is(lastName));
        }
    }

    @Test
    public void given_a_person_in_db_when_findById_invoked_then_person_is_returned() {
        Person person = given_a_list_of_people_persisted_in_mongo(1).get(0);

        Person insertedPerson = personRepository.findById(person.getId());
        assertThat(insertedPerson, is(person));
    }

    @Test
    public void given_people_inserted_in_db_and_a_name_a_lastName_and_an_age_when_invoked_a_list_of_people_with_that_values_is_returned() {
        given_a_list_of_people_persisted_in_mongo(100);
        String name = "person_name";
        String lastName = "person_lastname";
        int age = 50;
        List<Person> people = personRepository.findByNameAndLastNameOrAgeWithoutCountry(name, lastName, age);
        LOGGER.debug("{} people found with {} as name AND {} as lastName OR {} as age", people.size(), name, lastName, age);
        for (Person person : people) {
            assertTrue((person.getName().equals(name) && person.getLastName().equals(lastName)) || person.getAge() == age);
        }
    }

    @Test
    public void given_a_person_persisted_in_mongo_when_subsequent_updates_are_done_then_version_assures_no_data_is_lost() {
        expectedException.expect(OptimisticLockingFailureException.class);

        Person persontoUpdate = given_a_list_of_people_persisted_in_mongo(1).get(0);
        Person insertedPerson = personRepository.findOne(persontoUpdate.getId());

        assertThat(persontoUpdate.getVersion(), is(0L));
        assertThat(insertedPerson.getVersion(), is(0L));

        persontoUpdate.setAge(persontoUpdate.getAge() + 1);
        personRepository.save(persontoUpdate);

        assertThat(insertedPerson.getVersion(), is(0L));
        assertThat(persontoUpdate.getVersion(), is(1L));

        // should throw an OptimisticLockingFailureException
        personRepository.save(insertedPerson);
    }

    @Test
    public void given_a_partially_filled_person_persisted_in_db_when_updateSelective_invoked_only_informed_fields_are_updated() {
        DateTimeZone zone = DateTimeZone.getDefault();
//        DateTimeZone.setDefault(zone);

        Person partiallyFilledPerson = given_a_partially_filled_person();
        personRepository.insert(partiallyFilledPerson);

        Person updatedPerson = new Person();
        updatedPerson.setId(partiallyFilledPerson.getId());
        updatedPerson.setLastName("updated_person_lastname");
        updatedPerson.setBirthDate(LocalDateTime.now());
        updatedPerson.setAddress(AddressBuilder.builder()
                .city("updated_person_city")
                .addTags("tag_X", "tag_Y")
                .country((Country) CountryBuilder.builder()
                        .id(countries.get(ThreadLocalRandom.current().nextInt(countries.size())).getId())
                        .build())
                .build());

        int n = personRepository.updatePersonSelective(updatedPerson);
        assertThat(n, equalTo(1));
    }

    private List<Person> given_a_list_of_people_persisted_in_mongo(int size) {
        List<Person> people = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Person person = personRepository.insert(given_a_person());
            LOGGER.debug("{} inserted in mongoDB", person);
            people.add(person);
        }
        return people;
    }

    private Person given_a_partially_filled_person() {
        Person person = given_a_person();
        person.setLastName(null);
        person.setAge(null);
        person.setAddress(null);
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
                        .tags(tags.subList(0, ThreadLocalRandom.current().nextInt(tags.size())))
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

    private List<String> given_a_list_of_tags() {
        return asList("tag_1", "tag_2", "tag_3", "tag_4", "tag_5", "tag_6");
    }
}
