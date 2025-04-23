package pl.kamil_dywan.allegro.generated.delivery;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "street",
    "zipCode",
    "city",
    "countryCode"
})
@Generated("jsonschema2pojo")
public class DeliveryPickupPointAdress {

    @JsonProperty("street")
    private String street;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("countryCode")
    private String countryCode;

}
