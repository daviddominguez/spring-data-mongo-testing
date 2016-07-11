package es.amplia.commons.mongodb.model;

import com.google.common.base.Objects;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;

@TypeAlias("Address")
public class Address {
    private String address;
    private String zipcode;
    private String city;
    private String state;
    private List<String> tags;

    @DBRef
    private Country country;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address1 = (Address) o;
        return equal(address, address1.address) &&
                equal(zipcode, address1.zipcode) &&
                equal(city, address1.city) &&
                equal(state, address1.state) &&
                equal(country, address1.country) &&
                equal(tags, address1.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, zipcode, city, state, country, tags);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("address", address)
                .add("zipcode", zipcode)
                .add("city", city)
                .add("state", state)
                .add("country", country)
                .add("tags", tags)
                .omitNullValues()
                .toString();
    }
}
