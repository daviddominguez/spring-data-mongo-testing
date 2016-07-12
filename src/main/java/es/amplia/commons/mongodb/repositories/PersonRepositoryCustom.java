package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.model.Person;

import java.util.List;

public interface PersonRepositoryCustom {
    List<Person> findByNameAndLastNameOrAgeWithoutCountry(String name, String lastName, Integer age);
    int updatePersonSelective(Person person);
}
