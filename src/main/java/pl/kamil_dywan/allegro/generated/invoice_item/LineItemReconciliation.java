
package pl.kamil_dywan.allegro.generated.invoice_item;

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
import pl.kamil_dywan.allegro.generated.Cost;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "type",
    "quantity"
})
@Generated("jsonschema2pojo")
public class LineItemReconciliation {

    @JsonProperty("value")
    private Cost value;

    @JsonProperty("type")
    private String type;

    @JsonProperty("quantity")
    private Integer quantity;

}
