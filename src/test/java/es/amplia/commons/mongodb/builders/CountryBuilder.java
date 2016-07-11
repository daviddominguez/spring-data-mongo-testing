package es.amplia.commons.mongodb.builders;

import es.amplia.commons.mongodb.model.Country;
import es.amplia.commons.mongodb.model.Entity;

public class CountryBuilder extends EntityBuilder {

    public static CountryBuilder builder() {
        return new CountryBuilder();
    }

    @Override
    protected Country getEntity() {
        return (Country) super.getEntity();
    }

    protected Entity instantiateConcreteEntity() {
        return new Country();
    }

    public CountryBuilder country(String country) {
        getEntity().setCountry(country);
        return this;
    }

    public CountryBuilder countryISO(String countryISO) {
        getEntity().setCountryISO(countryISO);
        return this;
    }
}
