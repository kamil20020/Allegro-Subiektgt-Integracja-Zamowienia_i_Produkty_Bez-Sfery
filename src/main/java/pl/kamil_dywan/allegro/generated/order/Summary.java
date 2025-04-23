package pl.kamil_dywan.allegro.generated.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.generated.Cost;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalToPay"
})
@Generated("jsonschema2pojo")
public class Summary {

    @JsonProperty("totalToPay")
    private Cost totalToPay;

}
