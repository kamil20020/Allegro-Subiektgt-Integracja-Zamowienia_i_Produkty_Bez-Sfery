
package pl.kamil_dywan.external.allegro.generated.invoice_item;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rate",
    "subject",
    "exemption"
})
@Generated("jsonschema2pojo")
public class Tax {

    @JsonProperty("rate")
    private BigDecimal rate;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("exemption")
    private String exemption;

}
