package es.amplia.commons.mongodb.repositories;

import com.mongodb.WriteResult;
import es.amplia.commons.mongodb.model.Address;
import es.amplia.commons.mongodb.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Person> findByNameAndLastNameOrAgeWithoutCountry(String name, String lastName, Integer age) {
        return mongoOperations.find(
                query(new Criteria().orOperator(
                        where("name").is(name).and("lastName").is(lastName),
                        where("age").is(age))
                ),
                Person.class
        );
    }

    public int updatePersonSelective(Person person) {
        Update update = new Update();

        if (person.getName() != null)
            update.set("name", person.getName());
        if (person.getLastName() != null)
            update.set("lastName", person.getLastName());
        if (person.getAge() != null)
            update.set("age", person.getAge());
        if (person.getBirthDate() != null)
            update.set("birthDate", person.getBirthDate());
        if (person.getAddress() != null) {
            Address address = person.getAddress();
            if (address.getAddress() != null)
                update.set("address.address", address.getAddress());
            if (address.getCity() != null)
                update.set("address.city", address.getCity());
            if (address.getState() != null)
                update.set("address.state", address.getState());
            if (address.getCountry() != null)
                update.set("address.country", address.getCountry().getId());
            if (address.getZipcode() != null)
                update.set("address.zipcode", address.getZipcode());
            if (address.getTags() != null)
                update.addToSet("address.tags").each(address.getTags());
        }
        WriteResult wr = mongoOperations.updateFirst(query(where("id").is(person.getId())), update, Person.class);
        return wr.getN();
    }
}
