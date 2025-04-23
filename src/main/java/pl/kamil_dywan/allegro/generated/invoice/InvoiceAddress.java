package pl.kamil_dywan.allegro.generated.invoice;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;

@Data
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
public class InvoiceAddress {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("company")
    private InvoiceCompany company;

    @JsonProperty("naturalPerson")
    private InvoiceNaturalPerson naturalPerson;

}
