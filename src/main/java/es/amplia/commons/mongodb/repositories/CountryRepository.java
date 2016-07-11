package es.amplia.commons.mongodb.repositories;

import es.amplia.commons.mongodb.model.Country;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepository extends MongoRepository<Country, ObjectId> {
}
