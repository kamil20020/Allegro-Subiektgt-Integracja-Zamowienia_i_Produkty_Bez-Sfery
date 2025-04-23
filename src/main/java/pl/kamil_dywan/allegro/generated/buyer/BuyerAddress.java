package pl.kamil_dywan.allegro.generated.buyer;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.own.Country;

import javax.annotation.processing.Generated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "street",
    "city",
    "postCode",
    "countryCode"
})
@Generated("jsonschema2pojo")
public class BuyerAddress {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("countryCode")
    private Country countryCode;

}
