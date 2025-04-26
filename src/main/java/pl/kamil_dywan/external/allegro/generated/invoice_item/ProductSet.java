
package pl.kamil_dywan.external.allegro.generated.invoice_item;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "products"
})
@Generated("jsonschema2pojo")
public class ProductSet {

    @JsonProperty("products")
    private List<Product> products = new ArrayList<>();

}
