package es.amplia.commons.mongodb.model;

import com.google.common.base.Objects;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.google.common.base.MoreObjects.toStringHelper;

@TypeAlias("Country")
@Document(collection = "countries")
public class Country extends Entity {

    private String country;
    private String countryISO;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        if (!super.equals(o)) return false;
        Country country1 = (Country) o;
        return Objects.equal(country, country1.country) &&
                Objects.equal(countryISO, country1.countryISO);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), country, countryISO);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", getId())
                .add("version", getVersion())
                .add("country", country)
                .add("countryISO", countryISO)
                .toString();
    }
}
