package pl.kamil_dywan.allegro.generated.delivery;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.own.Country;

import javax.annotation.processing.Generated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "street",
    "zipCode",
    "city",
    "countryCode"
})
@Generated("jsonschema2pojo")
public class DeliveryPickupPointAddress {

    @JsonProperty("street")
    private String street;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("countryCode")
    private Country countryCode;

}
