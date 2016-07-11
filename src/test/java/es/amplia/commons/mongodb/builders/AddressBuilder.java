package es.amplia.commons.mongodb.builders;

import es.amplia.commons.mongodb.model.Address;
import es.amplia.commons.mongodb.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public class AddressBuilder {
    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    private final Address address = new Address();

    private AddressBuilder() {
    }

    public AddressBuilder address(String add) {
        address.setAddress(add);
        return this;
    }

    public AddressBuilder zipcode(String zc) {
        address.setZipcode(zc);
        return this;
    }

    public AddressBuilder city(String city) {
        address.setCity(city);
        return this;
    }

    public AddressBuilder state(String state) {
        address.setState(state);
        return this;
    }

    public AddressBuilder country(Country country) {
        address.setCountry(country);
        return this;
    }

    public AddressBuilder tags(List<String> tags) {
        address.setTags(tags);
        return this;
    }

    public AddressBuilder addTags(String... tags) {
        if (address.getTags() == null)
            address.setTags(new ArrayList<String>());
        address.getTags().addAll(asList(tags));
        return this;
    }

    public Address build() {
        return address;
    }
}