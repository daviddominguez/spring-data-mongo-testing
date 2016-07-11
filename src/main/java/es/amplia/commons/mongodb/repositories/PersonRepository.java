package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.model.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {
}
