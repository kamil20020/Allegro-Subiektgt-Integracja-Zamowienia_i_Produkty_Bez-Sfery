package pl.kamil_dywan.external.allegro.generated.invoice_item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "quantity"
})
public class Product {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("quantity")
    private Integer quantity;
}
