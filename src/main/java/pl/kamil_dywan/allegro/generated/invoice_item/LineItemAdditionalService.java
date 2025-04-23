package pl.kamil_dywan.allegro.generated.invoice_item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import pl.kamil_dywan.allegro.generated.Cost;

import javax.annotation.processing.Generated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "definitionId",
    "name",
    "price",
    "quantity",
})
@Generated("jsonschema2pojo")
public class LineItemAdditionalService {

    @JsonProperty("definitionId")
    private String definitionId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Cost price;

    @JsonProperty("quantity")
    private Integer quantity;

}
