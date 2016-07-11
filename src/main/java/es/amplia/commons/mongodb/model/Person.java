package es.amplia.commons.mongodb.model;

import com.google.common.base.Objects;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.math.BigDecimal;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;

@TypeAlias("Person")
@Document(collection = "people")
public class Person extends Entity {

    private String name;
    private String lastName;
    private Address address;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthDate;

    private Integer age;
    private BigDecimal salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return equal(name, person.name) &&
                equal(lastName, person.lastName) &&
                equal(address, person.address) &&
                equal(birthDate, person.birthDate) &&
                equal(age, person.age) &&
                equal(salary, person.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, lastName, address, birthDate, age, salary);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", getId())
                .add("version", getVersion())
                .add("name", name)
                .add("lastName", lastName)
                .add("address", address)
                .add("birthDate", birthDate)
                .add("age", age)
                .add("salary", salary)
                .toString();
    }
}
