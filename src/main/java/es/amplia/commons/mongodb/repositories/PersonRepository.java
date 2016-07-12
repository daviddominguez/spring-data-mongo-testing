package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.model.Person;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface PersonRepository extends MongoRepository<Person, ObjectId>, PersonRepositoryCustom {

    Person findById(ObjectId id);
    List<Person> findBySalaryBetween(BigDecimal from, BigDecimal to);
    List<Person> findByBirthDateAfter(LocalDateTime date);
    List<Person> findByAddressCountryIdIn(Collection<ObjectId> countries);
    List<Person> findByAddressTagsIn(Collection<String> tags);

    @Query(value = "{ 'name':?0 , 'lastName':?1}", fields = "{'address.country':0}")
    List<Person> findByFullname(String name, String lastName);
}
