package es.amplia.commons.mongodb.builders;

import es.amplia.commons.mongodb.model.Address;
import es.amplia.commons.mongodb.model.Entity;
import es.amplia.commons.mongodb.model.Person;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class PersonBuilder extends EntityBuilder {

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    @Override
    protected Person getEntity() {
        return (Person) super.getEntity();
    }

    protected Entity instantiateConcreteEntity() {
        return new Person();
    }

    public PersonBuilder name(String name) {
        getEntity().setName(name);
        return this;
    }

    public PersonBuilder lastName(String lastName) {
        getEntity().setLastName(lastName);
        return this;
    }

    public PersonBuilder address(Address address) {
        getEntity().setAddress(address);
        return this;
    }

    public PersonBuilder birthDate(int year, int month, int day) {
        getEntity().setBirthDate(new LocalDate(year, month, day));
        return this;
    }

    public PersonBuilder age(int age) {
        getEntity().setAge(age);
        return this;
    }

    public PersonBuilder salary(double salary) {
        getEntity().setSalary(BigDecimal.valueOf(salary));
        return this;
    }
}
