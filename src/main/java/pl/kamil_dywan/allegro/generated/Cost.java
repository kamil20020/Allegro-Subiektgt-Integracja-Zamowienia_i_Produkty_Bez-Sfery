package pl.kamil_dywan.allegro.generated;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import pl.kamil_dywan.allegro.own.Currency;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "currency"
})
@Generated("jsonschema2pojo")
public class Cost {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private Currency currency;

}
