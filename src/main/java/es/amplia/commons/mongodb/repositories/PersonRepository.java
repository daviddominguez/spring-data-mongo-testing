package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.model.Person;
import org.bson.types.ObjectId;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {

    Person findById(ObjectId id);
    List<Person> findBySalaryBetween(BigDecimal from, BigDecimal to);
    List<Person> findByBirthDateAfter(LocalDate date);
    List<Person> findByAddressCountryIdIn(Collection<ObjectId> countries);
    List<Person> findByAddressTagsIn(Collection<String> tags);
}
