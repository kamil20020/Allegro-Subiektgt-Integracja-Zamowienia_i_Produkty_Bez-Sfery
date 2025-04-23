package pl.kamil_dywan.allegro.generated.delivery;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "firstName",
    "lastName",
    "street",
    "city",
    "zipCode",
    "countryCode",
    "companyName",
    "phoneNumber",
    "modifiedAt"
})
@Generated("jsonschema2pojo")
public class DeliveryAddress {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("modifiedAt")
    private LocalDateTime modifiedAt;

    protected boolean canEqual(final Object other) {
        return other instanceof DeliveryAddress;
    }

}
