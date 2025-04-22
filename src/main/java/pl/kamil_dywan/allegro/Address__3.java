package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "street",
        "city",
        "zipCode",
        "countryCode",
        "company",
        "naturalPerson"
})
@Generated("jsonschema2pojo")
public class Address__3 {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("company")
    private Company company;

    @JsonProperty("naturalPerson")
    private NaturalPerson naturalPerson;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }


    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }


    @JsonProperty("city")
    public String getCity() {
        return city;
    }


    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }


    @JsonProperty("zipCode")
    public String getZipCode() {
        return zipCode;
    }


    @JsonProperty("zipCode")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }


    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    @JsonProperty("company")
    public Company getCompany() {
        return company;
    }


    @JsonProperty("company")
    public void setCompany(Company company) {
        this.company = company;
    }


    @JsonProperty("naturalPerson")
    public NaturalPerson getNaturalPerson() {
        return naturalPerson;
    }


    @JsonProperty("naturalPerson")
    public void setNaturalPerson(NaturalPerson naturalPerson) {
        this.naturalPerson = naturalPerson;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Address__3.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("street");
        sb.append('=');
        sb.append(((this.street == null) ? "<null>" : this.street));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null) ? "<null>" : this.city));
        sb.append(',');
        sb.append("zipCode");
        sb.append('=');
        sb.append(((this.zipCode == null) ? "<null>" : this.zipCode));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null) ? "<null>" : this.countryCode));
        sb.append(',');
        sb.append("company");
        sb.append('=');
        sb.append(((this.company == null) ? "<null>" : this.company));
        sb.append(',');
        sb.append("naturalPerson");
        sb.append('=');
        sb.append(((this.naturalPerson == null) ? "<null>" : this.naturalPerson));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.zipCode == null) ? 0 : this.zipCode.hashCode()));
        result = ((result * 31) + ((this.city == null) ? 0 : this.city.hashCode()));
        result = ((result * 31) + ((this.street == null) ? 0 : this.street.hashCode()));
        result = ((result * 31) + ((this.countryCode == null) ? 0 : this.countryCode.hashCode()));
        result = ((result * 31) + ((this.company == null) ? 0 : this.company.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.naturalPerson == null) ? 0 : this.naturalPerson.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address__3) == false) {
            return false;
        }
        Address__3 rhs = ((Address__3) other);
        return ((((((((this.zipCode == rhs.zipCode) || ((this.zipCode != null) && this.zipCode.equals(rhs.zipCode))) && ((this.city == rhs.city) || ((this.city != null) && this.city.equals(rhs.city)))) && ((this.street == rhs.street) || ((this.street != null) && this.street.equals(rhs.street)))) && ((this.countryCode == rhs.countryCode) || ((this.countryCode != null) && this.countryCode.equals(rhs.countryCode)))) && ((this.company == rhs.company) || ((this.company != null) && this.company.equals(rhs.company)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))) && ((this.naturalPerson == rhs.naturalPerson) || ((this.naturalPerson != null) && this.naturalPerson.equals(rhs.naturalPerson))));
    }

}
