package pl.kamil_dywan.allegro;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "email",
        "login",
        "firstName",
        "lastName",
        "companyName",
        "guest",
        "personalIdentity",
        "phoneNumber",
        "preferences",
        "address"
})
@Generated("jsonschema2pojo")
public class Buyer {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private String login;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("guest")
    private Boolean guest;

    @JsonProperty("personalIdentity")
    private String personalIdentity;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("preferences")
    private Preferences preferences;

    @JsonProperty("address")
    private Address address;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
